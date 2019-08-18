package my.miaosha.vo;

import javax.validation.constraints.NotNull;

import my.miaosha.validator.IsMobile;

public class LoginVo {
	
	@NotNull
	@IsMobile
	private long mobile;
	
	@NotNull
	private String password;

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginVo[mobile="+this.mobile+",password="+this.password+"]";
	}

}
