package com.learn.seckill.service.impl;

import com.learn.seckill.constant.RedisKey;
import com.learn.seckill.pojo.SkOrder;
import com.learn.seckill.pojo.SkOrderInfo;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.service.GoodsService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.service.SeckillService;
import com.learn.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class seckillServiceImpl implements SeckillService {

    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;
    @Transactional
    public SkOrderInfo seckill(SkUser user, GoodsVo goods) {
        //减库存
        boolean suc = goodsService.reduceStock(goods);
        if (suc){
            return orderService.createOrder(user,goods);
        }else{
            System.out.println("减库存失败");
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getSeckillResult(SkUser user, Long goodsId,Long secKillid){
        long userId = user.getId();
        SkOrder order = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long id) {

        if (redisService.hasKey(RedisKey.isGoodsOver+id)){
            return (boolean) redisService.get(RedisKey.isGoodsOver+id);
        }
       return false;
    }
    private void setGoodsOver(Long id) {
        redisService.set(RedisKey.isGoodsOver+id, true);
    }
}
