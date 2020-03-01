package com.tesla.crowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tesla.crowdfunding.bean.TPermission;
import com.tesla.crowdfunding.service.TPermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	TPermissionService permisionService;

	@GetMapping("/index")
	public String index() {
		return "permission/index";
	}

	@ResponseBody
	@GetMapping("/listAllPermissionTree")
	public List<TPermission> getAllPermissions() {
		return permisionService.getAllPermissions();
	}

	@ResponseBody
	@PostMapping("/add")
	public String addPermission(TPermission permission) {
		permisionService.savePermission(permission);
		return "ok";
	}

	@ResponseBody
	@DeleteMapping("/delete")
	public String deletePermission(Integer id) {
		permisionService.deletePermission(id);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/edit")
	public String editPermission(TPermission permission) {
		permisionService.editPermission(permission);
		return "ok";
	}

	@ResponseBody
	@GetMapping("/get")
	public TPermission getPermission(Integer id) {
		return permisionService.getPermissionById(id);
	}

}
