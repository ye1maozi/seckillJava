package com.learn.seckill.dao;

import com.learn.seckill.pojo.SkOrderInfo;

public interface SkOrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkOrderInfo record);

    int insertSelective(SkOrderInfo record);

    SkOrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkOrderInfo record);

    int updateByPrimaryKey(SkOrderInfo record);
}