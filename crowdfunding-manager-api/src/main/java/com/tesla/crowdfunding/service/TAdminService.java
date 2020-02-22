package com.tesla.crowdfunding.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.tesla.crowdfunding.bean.TAdmin;

public interface TAdminService {

	TAdmin getTAdminByLogin(Map<String, Object> paramMap);

	PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap);

	void saveTAdmin(TAdmin admin);

	TAdmin getTAdminById(Integer id);

	void updateTAdmin(TAdmin admin);

	void deleteTAdmin(Integer id);

	void deleteBatch(List<Integer> idList);

}
