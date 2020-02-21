package com.tesla.crowdfunding.service;

import java.util.Map;

import com.tesla.crowdfunding.bean.TAdmin;

public interface TAdminService {

	TAdmin getTAdminByLogin(Map<String, Object> paramMap);

}
