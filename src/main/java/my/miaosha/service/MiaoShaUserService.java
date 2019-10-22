package my.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.miaosha.dao.MiaoShaUserDao;
import my.miaosha.domain.User;
import my.miaosha.exception.GlobalException;
import my.miaosha.redis.RedisService;
import my.miaosha.redis.prefix.MiaoShaUserKey;
import my.miaosha.result.CodeMsg;
import my.miaosha.util.MD5Util;
import my.miaosha.util.UUIDUtils;
import my.miaosha.vo.LoginVo;

@Service
public class MiaoShaUserService {

	public static final String COOKI_NAME_TOKEN = "token";
	
	@Autowired
	private MiaoShaUserDao miaoShaUserDao;
	@Autowired
	private RedisService redisService;
	
	public User getById(long id) {
		//取缓存
		User user = redisService.get(MiaoShaUserKey.id, String.valueOf(id), User.class);
		if(user != null) {
			return user;
		}
		//取数据库
		user = miaoShaUserDao.getById(id);
		if(user != null) {
			redisService.set(MiaoShaUserKey.id, String.valueOf(user.getId()), user);
		}
		return user;
	}
	
	public boolean updatePassword(String token,long id,String password) {
		//取用户
		User user = getById(id);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_EMPTY);
		}
		//更新数据库
		String dbPassword = MD5Util.formPassToDBPass(password, user.getSalt());
		user.setPassword(dbPassword);
		miaoShaUserDao.updataPassword(user);
		//更新缓存
		redisService.delete(MiaoShaUserKey.id,String.valueOf(id));
		redisService.set(MiaoShaUserKey.token, token, user);
		return true;
	}
	
	public User getByToken(HttpServletResponse response,String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		User user = redisService.get(MiaoShaUserKey.token, token, User.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}
	
	public boolean login(HttpServletRequest request,HttpServletResponse response,LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		
		long mobile = loginVo.getMobile();
		String formPassword = loginVo.getPassword();
		
		User user = getById(mobile);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		
		String dbPassword = user.getPassword();
		String salt = user.getSalt();
		String pass = MD5Util.formPassToDBPass(formPassword, salt);

		if(!pass.equals(dbPassword)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		
		//判断缓存中是否有对应用户的token
		String token = getCookie(request,MiaoShaUserService.COOKI_NAME_TOKEN);
		
		//添加cookie
		if(StringUtils.isEmpty(token)) {
			token = UUIDUtils.createUUId();
		}
		addCookie(response, token, user);
		
		return true;
	}
	
	public String getCookie(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public void addCookie(HttpServletResponse response,String token,User user) {
		redisService.set(MiaoShaUserKey.token, token, user);
		Cookie cookie = new Cookie(MiaoShaUserService.COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
