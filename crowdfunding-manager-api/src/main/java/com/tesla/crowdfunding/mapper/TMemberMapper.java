package com.tesla.crowdfunding.mapper;

import com.tesla.crowdfunding.bean.TMember;
import com.tesla.crowdfunding.bean.TMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMemberMapper {
    long countByExample(TMemberExample example);

    int deleteByExample(TMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMember record);

    int insertSelective(TMember record);

    List<TMember> selectByExample(TMemberExample example);

    TMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByExample(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByPrimaryKeySelective(TMember record);

    int updateByPrimaryKey(TMember record);
}