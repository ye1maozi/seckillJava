package com.learn.seckill.service;

import com.learn.seckill.pojo.SkGoods;
import com.learn.seckill.vo.GoodsVo;

public interface GoodsService {
    Object listGoods();

    Object getDetailInfo(Long goodsId , Long seckillId);

    SkGoods getGoodById(long goodsId);

    boolean reduceStock(GoodsVo goods);

    GoodsVo getGoodsVoByGoodsId(long goodsId);
}
