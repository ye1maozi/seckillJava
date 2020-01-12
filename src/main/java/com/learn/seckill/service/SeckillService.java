package com.learn.seckill.service;

import com.learn.seckill.pojo.SkOrderInfo;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.vo.GoodsVo;

public interface SeckillService {
    SkOrderInfo seckill(SkUser user, GoodsVo goods);

    long getSeckillResult(SkUser user, Long goodsId,Long seckillId);
}
