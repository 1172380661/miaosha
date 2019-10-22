package my.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.miaosha.redis.RedisService;

@Service
public class MQSender {

	@Autowired
	AmqpTemplate amqpTemplate;
	
	@Autowired
	RedisService redisService;
	
	private Logger log = LoggerFactory.getLogger(MQSender.class);
	
	public <T> void sendMiaoshaMsg(T message) {
		log.info("send miaosha msg");
		String msg = redisService.beanToString(message);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
	}
	
}
