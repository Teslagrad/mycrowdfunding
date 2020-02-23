package com.tesla.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.bean.TRoleExample;
import com.tesla.crowdfunding.mapper.TRoleMapper;
import com.tesla.crowdfunding.service.TRoleService;

@Service
public class TRoleServiceImpl implements TRoleService {

	@Autowired
	TRoleMapper roleMapper;

	@Override
	public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {
		String condition = (String) paramMap.get("condition");

		List<TRole> list = null;

		if (StringUtils.isEmpty(condition)) {
			list = roleMapper.selectByExample(null);
		} else {
			TRoleExample example = new TRoleExample();
			example.createCriteria().andNameLike("%" + condition + "%");

			list = roleMapper.selectByExample(example);
		}

		PageInfo<TRole> page = new PageInfo<TRole>(list, 5);
		return page;
	}
}
