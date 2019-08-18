package my.miaosha.vo;

import java.util.Date;

import my.miaosha.domain.Goods;



public class GoodsVo extends Goods{
	private Long miaoshaGoodsId;
	private Double miaoshaPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
	
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Double miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}
	public Long getMiaoshaGoodsId() {
		return miaoshaGoodsId;
	}
	public void setMiaoshaGoodsId(Long miaoshaGoodsId) {
		this.miaoshaGoodsId = miaoshaGoodsId;
	}
}
