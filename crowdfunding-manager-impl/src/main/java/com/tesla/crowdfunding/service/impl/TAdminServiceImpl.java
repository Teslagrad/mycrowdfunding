package com.tesla.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.bean.TAdminExample;
import com.tesla.crowdfunding.bean.TAdminExample.Criteria;
import com.tesla.crowdfunding.bean.TProject;
import com.tesla.crowdfunding.bean.TProjectExample;
import com.tesla.crowdfunding.exception.LoginException;
import com.tesla.crowdfunding.mapper.TAdminMapper;
import com.tesla.crowdfunding.mapper.TProjectMapper;
import com.tesla.crowdfunding.service.TAdminService;
import com.tesla.crowdfunding.util.AppDateUtils;
import com.tesla.crowdfunding.util.Const;
import com.tesla.crowdfunding.util.MD5Util;

@Service
public class TAdminServiceImpl implements TAdminService {
	Logger log = LoggerFactory.getLogger(TAdminServiceImpl.class);
	@Autowired
	TAdminMapper adminMapper;

	@Autowired
	TProjectMapper projectMapper;

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

	@Override
	public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {
		String condition = (String) paramMap.get("condition");

		TAdminExample example = new TAdminExample();

		if (!"".equals(condition)) {
			example.createCriteria().andLoginacctLike("%" + condition + "%");
			Criteria criteria2 = example.createCriteria();
			criteria2.andUsernameLike("%" + condition + "%");

			Criteria criteria3 = example.createCriteria();
			criteria3.andEmailLike("%" + condition + "%");

			example.or(criteria2);
			example.or(criteria3);
		}
		// example.setOrderByClause("createtime desc");// 根据日期条件，倒叙

		List<TAdmin> list = adminMapper.selectByExample(example);

		PageInfo<TAdmin> page = new PageInfo<TAdmin>(list, 5);

		return page;
	}

	@Override
	public PageInfo<TProject> listProjectPage(Map<String, Object> paramMap) {
		String condition = (String) paramMap.get("condition");

		TProjectExample example = new TProjectExample();

		example.createCriteria().andStatusEqualTo("0");
		// example.setOrderByClause("createtime desc");// 根据日期条件，倒叙

		List<TProject> list = projectMapper.selectByExample(example);

		PageInfo<TProject> page = new PageInfo<TProject>(list, 5);

		return page;
	}

	@Override
	public void saveTAdmin(TAdmin admin) {

		admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));

		admin.setCreatetime(AppDateUtils.getFormatTime());

		adminMapper.insertSelective(admin); // 动态sql，有选择性保存，null值的不保存

	}

	@Override
	public TAdmin getTAdminById(Integer id) {

		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTAdmin(TAdmin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public void deleteTAdmin(Integer id) {

		adminMapper.deleteByPrimaryKey(id);

	}

	@Override
	public void updateProject(Integer id) {
		log.debug("----------------id----------------------------{}", id);

		TProject project = new TProject();
		project.setId(id);
		project.setStatus("1");

		log.debug("--------------------project------------------------{}", project);
		projectMapper.updateByPrimaryKeySelective(project);

	}

	@Override
	public void deleteBatch(List<Integer> idList) {

		adminMapper.deleteBatch(idList);// 自己定义一个批量删除的操作

	}

}
