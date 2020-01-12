package com.learn.seckill.service;

import com.learn.seckill.pojo.SkGoods;
import com.learn.seckill.pojo.SkOrder;
import com.learn.seckill.pojo.SkOrderInfo;
import com.learn.seckill.pojo.SkUser;

public interface OrderService {
    SkOrder getOrderByUserIdGoodsId(long userId,long goodsId);

    SkOrderInfo createOrder(SkUser user, SkGoods goods);

    void removeOrderByUserIdGoodsId(long userId, long goodsId) ;
}
