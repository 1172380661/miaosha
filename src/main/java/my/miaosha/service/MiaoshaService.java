package my.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.miaosha.dao.MiaoshaDao;
import my.miaosha.domain.OrderInfo;
import my.miaosha.domain.User;
import my.miaosha.vo.GoodsVo;

@Service
public class MiaoshaService {

	@Autowired
	MiaoshaDao miaoshaDao;
	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderService orderService;

	/**
	 * 减库存 下订单 写入秒杀订单
	 */
	@Transactional
	public OrderInfo miaosha(User user, GoodsVo goods) {
		//减库存
		long goodsId = goods.getId();
		long miaoshaGoodsId = goods.getMiaoshaGoodsId();
		goodsService.reduceGoodsStockById(goodsId);
		goodsService.reduceMiaoshaGoodsStockById(miaoshaGoodsId);
		//下订单
		OrderInfo order = orderService.createOrder(user,goods);
		
		return order;
	}
	
	
}
