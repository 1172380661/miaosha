package my.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.miaosha.domain.MiaoshaOrder;
import my.miaosha.domain.User;
import my.miaosha.redis.RedisService;
import my.miaosha.service.MiaoshaService;
import my.miaosha.service.OrderService;
import my.miaosha.vo.GoodsVo;
import my.miaosha.vo.MiaoshaVo;

@Service
public class MQReceiver {

	@Autowired
	private RedisService redisService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MiaoshaService miaoshaService;
	
	Logger log = LoggerFactory.getLogger(MQReceiver.class);
	
	@RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
	public void MiaoshaReceiver(String msg) {
		MiaoshaVo miaoshaVo = redisService.stringToBean(msg, MiaoshaVo.class);
		
		GoodsVo goods = miaoshaVo.getGoods();
		User user = miaoshaVo.getUser();
		//判断是否有足够库存
		if(goods.getGoodsStock()-1 <= 0) {
			return;
		}
		//判断是否已经进行过秒杀
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goods.getId());
		if(order != null) {
			return;
		}
		//减库存 下订单 写入秒杀订单
		miaoshaService.miaosha(user, goods);
	}
}
