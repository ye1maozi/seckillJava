package com.learn.seckill.dao;

import com.learn.seckill.pojo.SkGoods;
import com.learn.seckill.pojo.SkGoodsSeckill;
import com.learn.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkGoods record);

    int insertSelective(SkGoods record);

    SkGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkGoods record);

    int updateByPrimaryKeyWithBLOBs(SkGoods record);

    int updateByPrimaryKey(SkGoods record);

    List<SkGoods> selectAll();

    List<GoodsVo> listGoodsVo();

    GoodsVo listGoodsVoById(
            @Param("goodsId") Long goodsId,
            @Param("seckillId") Long seckillId);

    GoodsVo getGoodsVoByGoodsId(  @Param("goodsId")  long goodsId);
}