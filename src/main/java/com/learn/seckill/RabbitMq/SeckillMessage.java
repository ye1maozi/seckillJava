package com.learn.seckill.RabbitMq;

import com.learn.seckill.pojo.SkUser;

import java.io.Serializable;

public class SeckillMessage implements Serializable {

    private SkUser user;
    private long goodsId;

    private long seckillId;

    public SeckillMessage() {
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public SkUser getUser() {
        return user;
    }

    public void setUser(SkUser user) {
        this.user = user;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }
}
