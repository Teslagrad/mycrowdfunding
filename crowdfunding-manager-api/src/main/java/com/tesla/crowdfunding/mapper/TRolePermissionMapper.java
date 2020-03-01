package com.tesla.crowdfunding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tesla.crowdfunding.bean.TRolePermission;
import com.tesla.crowdfunding.bean.TRolePermissionExample;

public interface TRolePermissionMapper {
	long countByExample(TRolePermissionExample example);

	int deleteByExample(TRolePermissionExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TRolePermission record);

	int insertSelective(TRolePermission record);

	List<TRolePermission> selectByExample(TRolePermissionExample example);

	TRolePermission selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TRolePermission record,
			@Param("example") TRolePermissionExample example);

	int updateByExample(@Param("record") TRolePermission record, @Param("example") TRolePermissionExample example);

	int updateByPrimaryKeySelective(TRolePermission record);

	int updateByPrimaryKey(TRolePermission record);

	void saveRoleAndPermissionRelationship(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> ids);

	List<Integer> listPermissionIdByRoleId(Integer roleId);
}