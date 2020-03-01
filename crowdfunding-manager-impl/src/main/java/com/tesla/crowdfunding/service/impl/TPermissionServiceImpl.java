package com.tesla.crowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesla.crowdfunding.bean.TPermission;
import com.tesla.crowdfunding.bean.TPermissionMenuExample;
import com.tesla.crowdfunding.mapper.TPermissionMapper;
import com.tesla.crowdfunding.mapper.TPermissionMenuMapper;
import com.tesla.crowdfunding.mapper.TRolePermissionMapper;
import com.tesla.crowdfunding.service.TPermissionService;

@Service
public class TPermissionServiceImpl implements TPermissionService {

	@Autowired
	TPermissionMapper permissionMapper;

	@Autowired
	TRolePermissionMapper rolePermissionMapper;

	@Autowired
	TPermissionMenuMapper permissionMenuMapper;

	@Override
	public List<TPermission> getAllPermissions() {
		return permissionMapper.selectByExample(null);
	}

	@Override
	public void savePermission(TPermission permission) {
		permissionMapper.insertSelective(permission);
	}

	@Override
	public void deletePermission(Integer id) {
		permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void editPermission(TPermission permission) {
		permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public TPermission getPermissionById(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void assignPermissionToMenu(Integer mid, List<Integer> perIdArray) {
		// 1、删除之前菜单对应的权限
		TPermissionMenuExample example = new TPermissionMenuExample();
		example.createCriteria().andMenuidEqualTo(mid);
		permissionMenuMapper.deleteByExample(example);
		// 2、插入提交过来的新的权限集合
		permissionMenuMapper.insertBatch(mid, perIdArray);
	}

	@Override
	public List<TPermission> getPermissionByMenuid(Integer mid) {
		return permissionMapper.getPermissionByMenuid(mid);
	}
}
