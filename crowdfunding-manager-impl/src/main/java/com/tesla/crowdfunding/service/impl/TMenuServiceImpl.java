package com.tesla.crowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesla.crowdfunding.bean.TMenu;
import com.tesla.crowdfunding.mapper.TMenuMapper;
import com.tesla.crowdfunding.service.TMenuService;

@Service
public class TMenuServiceImpl implements TMenuService {

	Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);

	@Autowired
	TMenuMapper menuMapper;

	@Override
	public List<TMenu> listMenuAll() {

		List<TMenu> menuList = new ArrayList<TMenu>();// 只存在父菜单，但是将children属性赋值
//		方便查询
		Map<Integer, TMenu> cache = new HashMap<Integer, TMenu>();

		List<TMenu> allList = menuMapper.selectByExample(null);
		for (TMenu tMenu : allList) {
			if (tMenu.getPid() == 0) {
				menuList.add(tMenu);
				cache.put(tMenu.getId(), tMenu);
			}
		}
		for (TMenu tMenu : allList) {
			if (tMenu.getPid() != 0) {
				Integer pid = tMenu.getPid();
				TMenu parent = cache.get(pid);
				parent.getChildren().add(tMenu);// 根据子的父id找的父的map集合，得到父的子属性进行赋值，完成父子关系关联
			}
		}

		log.debug("菜单={}", menuList);

		return menuList;
	}

	@Override
	public List<TMenu> listMenuAllTree() {
		// TODO Auto-generated method stub
		return menuMapper.selectByExample(null);
	}
}
