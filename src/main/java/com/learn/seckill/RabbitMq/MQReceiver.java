package com.learn.seckill.RabbitMq;

import com.learn.seckill.pojo.SkGoods;
import com.learn.seckill.pojo.SkOrder;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.service.GoodsService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.service.SeckillService;
import com.learn.seckill.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(SeckillMessage message){
        log.info("secKill start  _ " + message.getGoodsId());
        SkUser user = message.getUser();
        long goodsId = message.getGoodsId();
     long id = message.getSeckillId();

        GoodsVo goods = (GoodsVo) goodsService.getDetailInfo(goodsId,id);
        int stock = goods.getGoodsStock();
        if (stock <= 0){
            return;
        }
//
//        SkOrder order = orderService.getOrderByUserIdGoodsId(user.getId(),goodsId);
//        if (order != null){
//            return;
//        }

        //下单
        seckillService.seckill(user,goods);
    }
}
