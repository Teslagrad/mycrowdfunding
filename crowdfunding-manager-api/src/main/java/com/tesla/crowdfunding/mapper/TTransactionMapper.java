package com.tesla.crowdfunding.mapper;

import com.tesla.crowdfunding.bean.TTransaction;
import com.tesla.crowdfunding.bean.TTransactionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TTransactionMapper {
    long countByExample(TTransactionExample example);

    int deleteByExample(TTransactionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TTransaction record);

    int insertSelective(TTransaction record);

    List<TTransaction> selectByExample(TTransactionExample example);

    TTransaction selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TTransaction record, @Param("example") TTransactionExample example);

    int updateByExample(@Param("record") TTransaction record, @Param("example") TTransactionExample example);

    int updateByPrimaryKeySelective(TTransaction record);

    int updateByPrimaryKey(TTransaction record);
}