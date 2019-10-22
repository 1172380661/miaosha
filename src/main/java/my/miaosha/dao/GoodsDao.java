package my.miaosha.dao;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import my.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {

	@Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on g.id=mg.goods_id")
	List<GoodsVo> listGoodsVo();
	
	@Select("select g.*,mg.id as miaosha_goods_id,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on g.id=mg.goods_id where g.id=#{goodsId}")
	GoodsVo getGoodsVoById(@Param("goodsId") long id);

	@Update("update goods set goods_stock = goods_stock-1 where id =#{id} and stock_count>0")
	void reduceGoodsStockById(@Param("id") long id);

	@Update("update miaosha_goods set stock_count = stock_count-1 where id =#{id} and stock_count>0")
	void reduceMiaoshaGoodsStockById(@Param("id") long id);
}
