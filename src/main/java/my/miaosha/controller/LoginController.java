package my.miaosha.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import my.miaosha.result.Result;
import my.miaosha.service.MiaoShaUserService;
import my.miaosha.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MiaoShaUserService miaoShaUserService;
	
	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/do_login")
	@ResponseBody
	public Result<Boolean> doLogin(@Valid LoginVo loginVo,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(loginVo.toString());
		miaoShaUserService.login(request,response,loginVo);
		return Result.success(true);
	}
}
