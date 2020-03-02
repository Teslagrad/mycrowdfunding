package com.tesla.crowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.service.TRoleService;
import com.tesla.crowdfunding.util.Datas;

@Controller
public class TRoleController {
	Logger log = LoggerFactory.getLogger(TRoleController.class);

	@Autowired
	TRoleService roleService;

	@ResponseBody
	@RequestMapping("/role/listPermissionIdByRoleId")
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {

		log.debug("roleId={}", roleId);

		List<Integer> list = roleService.listPermissionIdByRoleId(roleId);

		return list;
	}

	@ResponseBody
	@RequestMapping("/role/doAssignPermissionToRole")
	public String doAssignPermissionToRole(Integer roleId, Datas ds) {

		log.debug("roleId={}", roleId);
		log.debug("permissionIds={}", ds.getIds());

		roleService.saveRoleAndPermissionRelationship(roleId, ds.getIds());

		return "ok";
	}

	@ResponseBody // 数据转换,异步请求都要加
	@RequestMapping("/role/doDelete")
	public String doDelete(Integer id) {
		log.debug("开始删除");
		roleService.deleteTRole(id);

		return "ok";
	}

	@ResponseBody // 数据转换,异步请求都要加
	@RequestMapping("/role/doUpdate")
	public String doUpdate(TRole role) {
		roleService.updateTRole(role);

		log.debug("角色修改成功返回");
		return "ok";
	}

	@ResponseBody // 数据转换,异步请求都要加
	@RequestMapping("/role/getRoleById")
	public TRole getRoleById(Integer id) {

		log.debug("查询要修改的角色返回");
		return roleService.getRoleById(id);

	}

	// spring容器管service和dao，springmvc容器管controller
	@PreAuthorize("hasRole('PM - 项目经理')")
	@ResponseBody // 数据转换,异步请求都要加
	@RequestMapping("/role/doAdd")
	public String doAdd(TRole role) {
		roleService.saveTRole(role);

		log.debug("角色添加成功返回");
		return "ok";
	}

	/**
	 * 
	 * 启用了消息转换器，HttpMessageConverter
	 * 如果返回的结果为对象：启用这个转换器->MappingJackson2HttpMessageConverter 将对象序列化为json串
	 * 如果返回结果为string类型：启用这个转换器->StringHttpMessageConverter 将字符串原样输出
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody // 数据转换
	@RequestMapping("role/loadData")
	public PageInfo<TRole> loadData(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize,
			@RequestParam(value = "condition", required = false, defaultValue = "") String condition) {

		PageHelper.startPage(pageNum, pageSize);

		Map<String, Object> paramMap = new HashMap<String, Object>();// 考虑到参数可能变多，用集合存

		// paramMap.put(key, value);
		paramMap.put("condition", condition);

		PageInfo<TRole> page = roleService.listRolePage(paramMap);

		return page;// @responseBody转换为json串返回，不是做视图解析
	}

	@RequestMapping("role/index")
	public String index() {

		log.debug("跳到角色维护页面");
		return "role/index";
	}

}
