package com.tesla.crowdfunding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.bean.TAdminExample;

public interface TAdminMapper {
	long countByExample(TAdminExample example);

	int deleteByExample(TAdminExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TAdmin record);

	int insertSelective(TAdmin record);

	List<TAdmin> selectByExample(TAdminExample example);

	TAdmin selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TAdmin record, @Param("example") TAdminExample example);

	int updateByExample(@Param("record") TAdmin record, @Param("example") TAdminExample example);

	int updateByPrimaryKeySelective(TAdmin record);

	int updateByPrimaryKey(TAdmin record);

	void deleteBatch(@Param("idList") List<Integer> idList);
}