/**
 * @author : 孙留平
 * @since : 2018年10月10日 下午3:03:21
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.openide.util.io.SafeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.administrator.platform.model.LoginBean;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.LoginService;
import com.administrator.platform.vo.RegisterBean;
import com.administrator.platform.vo.ResponseData;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import io.swagger.annotations.Api;

/**
 * @author : Administrator
 * @since : 2018年10月10日 下午3:03:21
 * @see :
 */
@Controller
@Api("Login相关接口文档")
public class LoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoginService loginService;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(LoginController.class);

	@GetMapping(value = { "/login" })
	public String login() {
		return "page/login/login";
	}

	@GetMapping(value = { "/register" })
	public String toRegisterForm() {
		return "page/login/register";
	}

	public SysUser getUser() {
		LOGGER.info("getUser进来了");
		// 为了session从获取用户信息,可以配置如下
		SysUser user = new SysUser();
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth.getPrincipal() instanceof UserDetails) {
			user = (SysUser) auth.getPrincipal();
		}
		return user;
	}

	public HttpServletRequest getRequest() {
		LOGGER.info("getRequest进来了");
		return ((ServletRequestAttributes) RequestContextHolder
		        .getRequestAttributes()).getRequest();
	}

	@GetMapping("captcha.jpg")
	@ResponseBody
	public void captcha(HttpServletResponse response,
	        HttpServletRequest request) throws SafeException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到验证码到 session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录接口
	 */
	@PostMapping(value = "/login")
	public ResponseData login(@RequestBody LoginBean loginBean,
	        HttpServletRequest request) throws IOException {
		return ResponseData.getSuccessResult(
		        loginService.login(loginBean, request, authenticationManager));
	}

	@PostMapping("/logout")
	@ResponseBody
	public ResponseData logOut(@RequestParam("username") String username) {
		return ResponseData.getSuccessResult(loginService.logout(username));
	}

	@PostMapping("/register")
	public String register(@ModelAttribute RegisterBean registerBean) {
		if (null != loginService.register(registerBean)) {
			LOGGER.error("456");
			return "redirect:/login";
		} else {
			LOGGER.error("123");
			return "redircet:/register";
		}
	}
}
