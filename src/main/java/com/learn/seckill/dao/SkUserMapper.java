package com.learn.seckill.dao;

import com.learn.seckill.pojo.SkUser;
import org.apache.ibatis.annotations.Param;

public interface SkUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkUser record);

    int insertSelective(SkUser record);

    SkUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkUser record);

    int updateByPrimaryKey(SkUser record);

    SkUser getByMobile(Long mobile);

    SkUser getByMobileOrName(
            @Param("nickname") String nickname,
            @Param("mobile")  long parseLong);
}