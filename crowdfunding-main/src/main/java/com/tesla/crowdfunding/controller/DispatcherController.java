package com.tesla.crowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.bean.TMenu;
import com.tesla.crowdfunding.service.TAdminService;
import com.tesla.crowdfunding.service.TMenuService;
import com.tesla.crowdfunding.util.Const;

@Controller
public class DispatcherController {
	Logger log = LoggerFactory.getLogger(DispatcherController.class);

	@Autowired
	TMenuService menuService;

	@Autowired
	TAdminService adminService;

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

	@RequestMapping("/main")
	public String main(HttpSession session) {
		log.debug("跳转到后台系统main页面");
		// 存放的是父菜单
		List<TMenu> menuList = menuService.listMenuAll();
		session.setAttribute("menuList", menuList);
		return "main";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		log.debug("注销系统");
		if (session != null) {
			session.removeAttribute(Const.LOGIN_ADMIN);
			session.invalidate();
		}
		return "redirect:/index";
	}

	@RequestMapping("/doLgoin")
	public String doLgoin(String loginacct, String userpswd, HttpSession session, Model model) {
		log.debug("开始登录。。。。。。。。");
		log.debug("loginacct={}", loginacct);
		log.debug("userpswd={}", userpswd);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);

		try {
			TAdmin admin = adminService.getTAdminByLogin(paramMap);
			session.setAttribute(Const.LOGIN_ADMIN, admin);
			log.debug("登录成功");
			return "redirect:/main";
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("登录失败={}。。。", e.getMessage());
			model.addAttribute("message", e.getMessage());
			return "login";
		}

	}
}
