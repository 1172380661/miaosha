package my.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.miaosha.dao.GoodsDao;
import my.miaosha.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}
	
	public GoodsVo getGoodsVoById(long id) {
		return goodsDao.getGoodsVoById(id);
	}

	public void reduceGoodsStockById(long id) {
		goodsDao.reduceGoodsStockById(id);
	}

	public void reduceMiaoshaGoodsStockById(long id) {
		goodsDao.reduceMiaoshaGoodsStockById(id);
	}
}
