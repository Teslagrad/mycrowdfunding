package com.tesla.crowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.service.TAdminService;
import com.tesla.crowdfunding.service.TRoleService;

@Controller
public class TAdminController {
	Logger log = LoggerFactory.getLogger(TAdminController.class);

	@Autowired
	TAdminService adminService;

	@Autowired
	TRoleService roleService;

	@ResponseBody
	@RequestMapping("/admin/doUnAssign")
	public String doUnAssign(Integer[] roleId, Integer adminId) {

		log.debug("adminId={}", adminId);
		for (Integer rId : roleId) {
			log.debug("roleId={}", rId);
		}

		roleService.deleteAdminAndRoleRelationship(roleId, adminId);

		return "ok";
	}

	// "id=5&id=7&id=8&adminId=1"
	@ResponseBody
	@RequestMapping("/admin/doAssign")
	public String doAssign(Integer[] roleId, Integer adminId) {

		log.debug("adminId={}", adminId);
		for (Integer rId : roleId) {
			log.debug("roleId={}", rId);
		}

		roleService.saveAdminAndRoleRelationship(roleId, adminId);

		return "ok";
	}

	@RequestMapping("/admin/toAssign")
	public String toAssign(String id, Model model) {

		// 1.查询所有角色
		List<TRole> allList = roleService.listAllRole();

		// 2.根据用户id查询已经拥有的角色id
		List<Integer> roleIdList = roleService.getRoleIdByAdminId(id);

		// 3.将所有角色，进行划分：
		List<TRole> assignList = new ArrayList<TRole>();
		List<TRole> unAssignList = new ArrayList<TRole>();

		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);

		for (TRole role : allList) {
			if (roleIdList.contains(role.getId())) {// 4.已分配角色集合
				assignList.add(role);
			} else {// 5.未分配角色集合
				unAssignList.add(role);
			}
		}
		return "admin/assignRole";
	}

	@RequestMapping("/admin/doDeleteBatch") // ids="1,2,3";
	public String doDeleteBatch(String ids, Integer pageNum) {

		String[] split = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();

		for (String idstr : split) {
			int id = Integer.parseInt(idstr);
			idList.add(id);
		}

		adminService.deleteBatch(idList);

		return "redirect:/admin/index?pageNum=" + pageNum;
	}

	@RequestMapping("/admin/doDelete")
	public String doDelete(Integer id, Integer pageNum) {
		log.debug("处理删除。。。");
		adminService.deleteTAdmin(id);

		log.debug("删除成功跳转回用户维护页面当前页");
		return "redirect:/admin/index?pageNum=" + pageNum;
	}

	@RequestMapping("/admin/doUpdate")
	public String doUpdate(TAdmin admin, Integer pageNum) {
		log.debug("表单提交成功");
		adminService.updateTAdmin(admin);

		log.debug("修改成功跳转回用户维护页面当前页");
		return "redirect:/admin/index?pageNum=" + pageNum;
	}

	@RequestMapping("/admin/toUpdate")
	public String toUpdate(Integer id, Model model) {

		TAdmin admin = adminService.getTAdminById(id);
		model.addAttribute("admin", admin);
		log.debug("跳转到修改页面");
		return "admin/update";
	}

	@RequestMapping("/admin/toAdd")
	public String toAdd() {
		log.debug("跳转到新增页面");
		return "admin/add";
	}

	@RequestMapping("/admin/doAdd")
	public String doAdd(TAdmin admin) {
		log.debug("表单提交成功");
		adminService.saveTAdmin(admin);

		log.debug("添加成功跳转回用户维护页面最后一页");
		// return "redirect:/admin/index";
		return "redirect:/admin/index?pageNum=" + Integer.MAX_VALUE;//
		// 分页合理化，没这么多也，就到最后一页；在spring和mybatis的xml文件中添加分页合理化
	}

	@RequestMapping("/admin/index") // 你锁映射的模块的路径名
	public String index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize, Model model,
			@RequestParam(value = "condition", required = false, defaultValue = "") String condition) {
		log.debug("pageNum={}", pageNum);
		log.debug("pageSize={}", pageSize);
		log.debug("condition={}", condition);

		PageHelper.startPage(pageNum, pageSize);// 线程绑定

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("condition", condition);

		PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);

		model.addAttribute("page", page);

		log.debug("跳转到admin/index。。。。。");

		return "admin/index";// 你要查找的资源文件夹的名，最好一样，好阅读
	}

}
