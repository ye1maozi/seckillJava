package com.learn.seckill.service.impl;

import com.learn.seckill.dao.SkOrderInfoMapper;
import com.learn.seckill.dao.SkOrderMapper;
import com.learn.seckill.pojo.SkGoods;
import com.learn.seckill.pojo.SkOrder;
import com.learn.seckill.pojo.SkOrderInfo;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@CacheConfig(cacheNames = "sk_order")
public class OrderServiceImpl implements OrderService {

    @Autowired
    SkOrderInfoMapper orderInfoMapper;
    @Autowired
    SkOrderMapper orderMapper;

    @Cacheable(key = "'orderid_' + #p0 +'_' + #p1")
    public SkOrder getOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderMapper.selectByUserAndGoods(userId,goodsId);
    }
    @CacheEvict(key = "'orderid_' + #p0 +'_' + #p1")
    public void removeOrderByUserIdGoodsId(long userId, long goodsId) {
    }
    @Override
    @Transactional
    public SkOrderInfo createOrder(SkUser user, SkGoods goods) {
        SkOrderInfo orderInfo = new SkOrderInfo();
        orderInfo.setGoodsCount(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setDevliveryAddrId(0L);
        orderInfo.setUserId(user.getId());
        orderInfo.setOrderChannel((byte) 1);
        orderInfo.setStatus((byte) 0);
        orderInfoMapper.insertSelective(orderInfo);

        SkOrder order = new SkOrder();
        order.setGoodsId(goods.getId());
        order.setUserId(user.getId());
        order.setCreateTime(new Date());
        order.setOrderId(orderInfo.getId());
        orderMapper.insertSelective(order);

        OrderService orderService = SpringContextUtil.getBean(OrderService.class);
        orderService.removeOrderByUserIdGoodsId(user.getId(),goods.getId());
        return orderInfo;
    }
}
