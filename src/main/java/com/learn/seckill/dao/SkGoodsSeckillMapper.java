package com.learn.seckill.dao;

import com.learn.seckill.pojo.SkGoodsSeckill;
import org.apache.ibatis.annotations.Param;

public interface SkGoodsSeckillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkGoodsSeckill record);

    int insertSelective(SkGoodsSeckill record);

    SkGoodsSeckill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkGoodsSeckill record);

    int updateByPrimaryKey(SkGoodsSeckill record);

    int getVersionByGoodsId( @Param("goodsId") Long goodsId,
                             @Param("seckillId") Long seckillId);

    int reduceStockByVersion(@Param("goodsId") Long goodsId,
                             @Param("seckillId") Long seckillId,@Param("version") Integer version);

}