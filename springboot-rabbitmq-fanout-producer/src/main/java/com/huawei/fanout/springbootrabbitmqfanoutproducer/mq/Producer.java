package com.huawei.fanout.springbootrabbitmqfanoutproducer.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/22
 * @since 1.0.0
 */
@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private AmqpTemplate amqpTemplate;

    @Value("${order.fanout.exchange}")
    private String exchange_name;


    public void sendMessage(){
        String orderId = UUID.randomUUID().toString();
        String msg = "你的订单消息：" + orderId + "，日期：" + new Date();
        System.out.println("[producer] send msg:" + msg);
        rabbitTemplate.convertAndSend(exchange_name, "", msg);
    }
}
