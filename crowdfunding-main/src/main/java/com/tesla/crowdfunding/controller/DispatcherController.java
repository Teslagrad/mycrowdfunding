package com.tesla.crowdfunding.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DispatcherController {
	Logger log = LoggerFactory.getLogger(DispatcherController.class);

	@RequestMapping("/index")
	public String index() {
		log.debug("跳转到系统主页面...");
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		log.debug("跳转到登录的主页面");
		return "login";
	}

	@RequestMapping("/doLgoin")
	public String doLgoin(String loginacct, String userpswd) {
		log.debug("开始登录。。。。。。。。");
		log.debug("loginacct={}", loginacct);
		log.debug("userpswd={}", userpswd);
		return "main";
	}
}
