package com.tesla.crowdfunding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tesla.crowdfunding.bean.TPermissionMenu;
import com.tesla.crowdfunding.bean.TPermissionMenuExample;

public interface TPermissionMenuMapper {
	long countByExample(TPermissionMenuExample example);

	int deleteByExample(TPermissionMenuExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TPermissionMenu record);

	int insertSelective(TPermissionMenu record);

	List<TPermissionMenu> selectByExample(TPermissionMenuExample example);

	TPermissionMenu selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TPermissionMenu record,
			@Param("example") TPermissionMenuExample example);

	int updateByExample(@Param("record") TPermissionMenu record, @Param("example") TPermissionMenuExample example);

	int updateByPrimaryKeySelective(TPermissionMenu record);

	int updateByPrimaryKey(TPermissionMenu record);

	void insertBatch(@Param("mid") Integer mid, @Param("perIdArray") List<Integer> perIdArray);
}