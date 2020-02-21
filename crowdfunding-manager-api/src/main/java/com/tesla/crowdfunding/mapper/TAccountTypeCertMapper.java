package com.tesla.crowdfunding.mapper;

import com.tesla.crowdfunding.bean.TAccountTypeCert;
import com.tesla.crowdfunding.bean.TAccountTypeCertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAccountTypeCertMapper {
    long countByExample(TAccountTypeCertExample example);

    int deleteByExample(TAccountTypeCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAccountTypeCert record);

    int insertSelective(TAccountTypeCert record);

    List<TAccountTypeCert> selectByExample(TAccountTypeCertExample example);

    TAccountTypeCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAccountTypeCert record, @Param("example") TAccountTypeCertExample example);

    int updateByExample(@Param("record") TAccountTypeCert record, @Param("example") TAccountTypeCertExample example);

    int updateByPrimaryKeySelective(TAccountTypeCert record);

    int updateByPrimaryKey(TAccountTypeCert record);
}