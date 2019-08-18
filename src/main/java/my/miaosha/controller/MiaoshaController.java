package my.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.miaosha.domain.MiaoshaOrder;
import my.miaosha.domain.User;
import my.miaosha.rabbitmq.MQSender;
import my.miaosha.redis.RedisService;
import my.miaosha.redis.prefix.MiaoshaKey;
import my.miaosha.result.CodeMsg;
import my.miaosha.result.Result;
import my.miaosha.service.GoodsService;
import my.miaosha.service.MiaoshaService;
import my.miaosha.service.OrderService;
import my.miaosha.vo.GoodsVo;
import my.miaosha.vo.MiaoshaVo;

@Controller
@RequestMapping(value = "/miaosha")
public class MiaoshaController {

	@Autowired
	MiaoshaService miaoshaService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	MQSender mqSender;
	
	@RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
	@ResponseBody
	public Result<Integer> doMiaosha(Model model,@RequestParam("goodsId") long goodsId,User user) {
		if(user == null) {
			return Result.error(CodeMsg.USER_UNLOGIN);
		}
		//判断是否已经秒杀过
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if(order != null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		//预减库存
		long stock = redisService.decr(MiaoshaKey.stock, goodsId);
		if(stock < 0) {
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		GoodsVo goods = goodsService.getGoodsVoById(goodsId);
		MiaoshaVo miaoshaVo = new MiaoshaVo();
		miaoshaVo.setGoods(goods);
		miaoshaVo.setUser(user);
		//消息入队
		mqSender.sendMiaoshaMsg(miaoshaVo);
		return Result.success(0);
	}
	
	@RequestMapping(value = "/getResult")
	@ResponseBody
	public Result<CodeMsg> getResult(User user,@RequestParam(value = "goodsId") String goodsId){
		if(user == null) {
			return Result.error(CodeMsg.USER_UNLOGIN);
		}
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), Long.getLong(goodsId));
		if(order == null) {
			int stock = redisService.get(MiaoshaKey.stock, goodsId, int.class);
			if(stock < 0) {
				return Result.error(CodeMsg.MIAO_SHA_OVER);
			}else {
				return Result.error(CodeMsg.WAIT_QUEUE);
			}
		}else {
			return Result.success(CodeMsg.SUCCESS);
		}
	}
}
