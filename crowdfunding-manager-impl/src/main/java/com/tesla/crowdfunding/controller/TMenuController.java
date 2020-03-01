package com.tesla.crowdfunding.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tesla.crowdfunding.bean.TMenu;
import com.tesla.crowdfunding.service.TMenuService;

@Controller
public class TMenuController {
	@Autowired
	TMenuService menuService;

	Logger log = LoggerFactory.getLogger(TMenuController.class);

	@ResponseBody
	@RequestMapping("/menu/loadTree")
	public List<TMenu> loadTree() {
		return menuService.listMenuAllTree();
	}

	@RequestMapping("/menu/index")
	public String index() {
		log.debug("跳转到菜单树");
		return "menu/index";
	}

}
