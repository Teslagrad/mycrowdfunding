package com.tesla.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.bean.TRoleExample;
import com.tesla.crowdfunding.bean.TRolePermissionExample;
import com.tesla.crowdfunding.mapper.TAdminRoleMapper;
import com.tesla.crowdfunding.mapper.TRoleMapper;
import com.tesla.crowdfunding.mapper.TRolePermissionMapper;
import com.tesla.crowdfunding.service.TRoleService;

@Service
public class TRoleServiceImpl implements TRoleService {

	@Autowired
	TRoleMapper roleMapper;

	@Autowired
	TAdminRoleMapper adminRoleMapper;

	@Autowired
	TRolePermissionMapper rolePermissionMapper;

	@Override
	public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {
		String condition = (String) paramMap.get("condition");

		List<TRole> list = null;

		if (StringUtils.isEmpty(condition)) {
			list = roleMapper.selectByExample(null);
		} else {
			TRoleExample example = new TRoleExample();
			example.createCriteria().andNameLike("%" + condition + "%");

			// example.setOrderByClause("id desc");// 根据id条件，倒叙

			list = roleMapper.selectByExample(example);
		}

		PageInfo<TRole> page = new PageInfo<TRole>(list, 5);
		return page;
	}

	// @PreAuthorize("hasRole('PM - 项目经理')")
	@Override
	public void saveTRole(TRole role) {

		roleMapper.insertSelective(role);
	}

	@Override
	public TRole getRoleById(Integer id) {

		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTRole(TRole role) {

		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void deleteTRole(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<TRole> listAllRole() {
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<Integer> getRoleIdByAdminId(String id) {
		return adminRoleMapper.getRoleIdByAdminId(id);
	}

	@Override
	public void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
		adminRoleMapper.saveAdminAndRoleRelationship(roleId, adminId);
	}

	@Override
	public void deleteAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {

		adminRoleMapper.deleteAdminAndRoleRelationship(roleId, adminId);
	}

	@Override
	public void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids) {
		// 把所有权限去掉，再一次性把打勾的加上
		TRolePermissionExample example = new TRolePermissionExample();

		example.createCriteria().andRoleidEqualTo(roleId);

		rolePermissionMapper.deleteByExample(example);

		rolePermissionMapper.saveRoleAndPermissionRelationship(roleId, ids);
	}

	@Override
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {

		return rolePermissionMapper.listPermissionIdByRoleId(roleId);
	}
}
