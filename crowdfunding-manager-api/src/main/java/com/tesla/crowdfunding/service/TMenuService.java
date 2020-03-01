package com.tesla.crowdfunding.service;

import java.util.List;

import com.tesla.crowdfunding.bean.TMenu;

public interface TMenuService {

	List<TMenu> listMenuAll();// 组合了父子关系的

	List<TMenu> listMenuAllTree();

	void deleteTMenu(Integer id);

	void updateTMenu(TMenu menu);

	TMenu getMenuById(Integer id);

	void saveTMenu(TMenu menu);

}
