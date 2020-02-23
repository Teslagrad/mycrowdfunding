package com.tesla.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.mapper.TRoleMapper;
import com.tesla.crowdfunding.service.TRoleService;

@Service
public class TRoleServiceImpl implements TRoleService {

	@Autowired
	TRoleMapper roleMapper;

	@Override
	public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {

		List<TRole> list = roleMapper.selectByExample(null);

		PageInfo<TRole> page = new PageInfo<TRole>(list, 5);
		return page;
	}
}
