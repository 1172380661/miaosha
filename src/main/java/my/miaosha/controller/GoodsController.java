package my.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import my.miaosha.domain.User;
import my.miaosha.redis.RedisService;
import my.miaosha.redis.prefix.HtmlPrefix;
import my.miaosha.redis.prefix.MiaoshaKey;
import my.miaosha.result.CodeMsg;
import my.miaosha.result.Result;
import my.miaosha.service.GoodsService;
import my.miaosha.vo.DetailVo;
import my.miaosha.vo.GoodsVo;



@Controller
@RequestMapping("/goods")
public class GoodsController implements InitializingBean{
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;
	
	@Autowired
	ApplicationContext applicationContext;
	
	
	/**
	 * 并发 5000*10
	 * 无页面缓存时：QPS 580
	 * 运用页面缓存时：QPS
	 */
	@RequestMapping(value = "/to_goods",produces = "text/html")
	@ResponseBody
	public String toGoods(HttpServletRequest request,HttpServletResponse response,Model model) {
		//取缓存
		String html = redisService.get(HtmlPrefix.goodsList, "", String.class);
		if(!StringUtils.isEmpty(html)) {
			return html;
		}
		
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		
		//手动渲染
		SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
		if(!StringUtils.isEmpty(html)) {
			redisService.set(HtmlPrefix.goodsList, "", html);
		}
		return html;
	}
	
	@RequestMapping(value = "/to_detail/{goodsId}",produces="text/html")
	@ResponseBody
	public String toDetail(HttpServletRequest request,HttpServletResponse response,Model model,User user,
			@PathVariable(value = "goodsId") long id) {
		//取缓存
		String html = redisService.get(HtmlPrefix.goodsDetail, String.valueOf(id), String.class);
		if(!StringUtils.isEmpty(html)) {
			return html;
		}
		
		GoodsVo goods = goodsService.getGoodsVoById(id);
//		if(user == null) {
//			return "login";
//		}
		
		long start = goods.getStartDate().getTime();
		long end = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();
		int remainSeconds = 0;
		int miaoshaStatus = 0;
		
		if(now < start) {//秒杀未开始，倒计时
			miaoshaStatus = 0;
			remainSeconds =(int)((start - now)/1000);
		}else if(now > end){//秒杀结束
			miaoshaStatus = 2;
			remainSeconds =-1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("goods", goods);
		model.addAttribute("user", user);
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		
		//手动渲染
		SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
		if(!StringUtils.isEmpty(html)) {
			redisService.set(HtmlPrefix.goodsDetail, String.valueOf(id), html);
		}
		return html;
	}
	
	@RequestMapping(value = "/detail/{goodsId}")
	@ResponseBody
	public Result<? extends Object> detail(HttpServletRequest request,HttpServletResponse response,Model model,User user,
			@PathVariable(value = "goodsId") long id) {
		if(user == null) {
			return Result.error(CodeMsg.USER_EMPTY);
		}
		GoodsVo goods = goodsService.getGoodsVoById(id);
		long start = goods.getStartDate().getTime();
		long end = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();
		int remainSeconds = 0;
		int miaoshaStatus = 0;
		
		if(now < start) {//秒杀未开始，倒计时
			miaoshaStatus = 0;
			remainSeconds =(int)((start - now)/1000);
		}else if(now > end){//秒杀结束
			miaoshaStatus = 2;
			remainSeconds =-1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		DetailVo detailVo = new DetailVo();
		detailVo.setGoods(goods);
		detailVo.setUser(user);
		detailVo.setMiaoshaStatus(miaoshaStatus);
		detailVo.setRemainSeconds(remainSeconds);
		return Result.success(detailVo);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> listGoodsVo = goodsService.listGoodsVo();
		if(listGoodsVo == null) {
			return;
		}
		for (GoodsVo goodsVo : listGoodsVo) {
			long id = goodsVo.getId();
			redisService.set(MiaoshaKey.stock, String.valueOf(id), goodsVo.getStockCount());
		}
	}
}
