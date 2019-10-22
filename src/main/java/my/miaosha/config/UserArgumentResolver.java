package my.miaosha.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import my.miaosha.domain.User;
import my.miaosha.service.MiaoShaUserService;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	@Autowired
	MiaoShaUserService userService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz == User.class ;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		String paramToken = request.getParameter(MiaoShaUserService.COOKI_NAME_TOKEN);
		String cookieToken = userService.getCookie(request,MiaoShaUserService.COOKI_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(cookieToken)?paramToken:cookieToken;
		return userService.getByToken(response,token);
	}

}
