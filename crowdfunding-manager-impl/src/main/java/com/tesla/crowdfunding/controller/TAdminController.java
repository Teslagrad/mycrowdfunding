package com.tesla.crowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.service.TAdminService;

@Controller
public class TAdminController {
	Logger log = LoggerFactory.getLogger(TAdminController.class);

	@Autowired
	TAdminService adminService;

	@RequestMapping("/admin/index") // 你锁映射的模块的路径名
	public String index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageNum", required = false, defaultValue = "10") Integer pageSize, Model model) {

		PageHelper.startPage(pageNum, pageSize);// 线程绑定

		Map<String, Object> paramMap = new HashMap<String, Object>();

		PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);

		model.addAttribute("page", page);

		log.debug("跳转到admin/index。。。。。");

		return "admin/index";// 你要查找的资源文件夹的名，最好一样，好阅读
	}
}
