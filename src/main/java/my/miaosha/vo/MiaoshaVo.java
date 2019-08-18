package my.miaosha.vo;

import my.miaosha.domain.User;

public class MiaoshaVo {

	private GoodsVo goods; 
	
	private User User;

	public GoodsVo getGoods() {
		return goods;
	}

	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	} 
	
}
