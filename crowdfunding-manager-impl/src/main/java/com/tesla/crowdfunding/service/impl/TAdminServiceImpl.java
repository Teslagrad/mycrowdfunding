package com.tesla.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.bean.TAdminExample;
import com.tesla.crowdfunding.exception.LoginException;
import com.tesla.crowdfunding.mapper.TAdminMapper;
import com.tesla.crowdfunding.service.TAdminService;
import com.tesla.crowdfunding.util.Const;
import com.tesla.crowdfunding.util.MD5Util;

@Service
public class TAdminServiceImpl implements TAdminService {
	Logger log = LoggerFactory.getLogger(TAdminServiceImpl.class);
	@Autowired
	TAdminMapper adminMapper;

	@Override
	public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
		// 1.密码加密
		// 2.查询用户
		String loginacct = (String) paramMap.get("loginacct");
		String userpswd = (String) paramMap.get("userpswd");

		// 3.判断用户是否为null
		// 不指定就是查所有
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);

		List<TAdmin> list = adminMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
		}

		TAdmin admin = list.get(0);
		// 4.判断密码是否一致
		if (!admin.getUserpswd().equals(MD5Util.digest(userpswd))) {
			throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
		}
		// 5.返回结果
		return admin;
	}
//	@Override
//	public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
//		// 1.密码加密
//		// 2.查询用户
//		String loginacct = (String) paramMap.get("loginacct");
//		String userpswd = (String) paramMap.get("userpswd");
//
//		// 3.判断用户是否为null
//		// 不指定就是查所有
//		TAdminExample example = new TAdminExample();
//		example.createCriteria().andLoginacctEqualTo(loginacct);
//
//		List<TAdmin> list = adminMapper.selectByExample(example);
//		if (list != null && list.size() == 1) {
//			TAdmin admin = list.get(0);
//
//			// 4.判断密码是否一致
//			if (admin.getUserpswd().equals(userpswd)) {
//				// 5.返回结果
//				return admin;
//			} else {
//				throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
//			}
//		} else {
//			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
//		}
//	}
}
