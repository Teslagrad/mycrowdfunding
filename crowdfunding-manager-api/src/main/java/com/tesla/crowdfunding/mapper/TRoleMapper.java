package com.tesla.crowdfunding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.bean.TRoleExample;

public interface TRoleMapper {
	long countByExample(TRoleExample example);

	int deleteByExample(TRoleExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TRole record);

	int insertSelective(TRole record);

	List<TRole> selectByExample(TRoleExample example);

	TRole selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TRole record, @Param("example") TRoleExample example);

	int updateByExample(@Param("record") TRole record, @Param("example") TRoleExample example);

	int updateByPrimaryKeySelective(TRole record);

	int updateByPrimaryKey(TRole record);

}