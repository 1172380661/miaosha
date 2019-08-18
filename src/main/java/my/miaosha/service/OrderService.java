package my.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.miaosha.dao.OrderDao;
import my.miaosha.domain.MiaoshaOrder;
import my.miaosha.domain.OrderInfo;
import my.miaosha.domain.User;
import my.miaosha.redis.RedisService;
import my.miaosha.redis.prefix.OrderKey;
import my.miaosha.vo.GoodsVo;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	@Autowired
	RedisService redisService;

	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return redisService.get(OrderKey.MIAOSHA_ORDER, userId+""+goodsId, MiaoshaOrder.class);
	}

	public OrderInfo createOrder(User user, GoodsVo goods) {
		long userId = user.getId();
		long goodsId = goods.getId();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goodsId);
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(userId);
		long orderId = orderDao.insert(orderInfo);
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goodsId);
		miaoshaOrder.setOrderId(orderId);
		miaoshaOrder.setUserId(userId);
		orderDao.insertMiaoshaOrder(miaoshaOrder);
		redisService.set(OrderKey.MIAOSHA_ORDER, userId+"_"+goodsId, miaoshaOrder);
		return orderInfo;
	}

	public OrderInfo getOrderById(long orderId) {
		OrderInfo orderInfo = orderDao.getOrderById(orderId);
		return orderInfo;
	}
	
}
