package com.learn.seckill.RabbitMq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;


    public void sendSeckillMessage(SeckillMessage message){
        amqpTemplate.convertAndSend(MQConfig.QUEUE,message);
    }

}
