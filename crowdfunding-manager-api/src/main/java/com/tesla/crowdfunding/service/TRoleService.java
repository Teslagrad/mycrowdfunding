package com.tesla.crowdfunding.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TRole;

public interface TRoleService {

	PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

}
