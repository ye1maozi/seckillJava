package com.learn.seckill.dao;

import com.learn.seckill.pojo.SkOrder;
import org.apache.ibatis.annotations.Param;

public interface SkOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkOrder record);

    int insertSelective(SkOrder record);

    SkOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkOrder record);

    int updateByPrimaryKey(SkOrder record);

    SkOrder selectByUserAndGoods(
            @Param("userId")  long userId,
            @Param("goodsId") long goodsId);
}