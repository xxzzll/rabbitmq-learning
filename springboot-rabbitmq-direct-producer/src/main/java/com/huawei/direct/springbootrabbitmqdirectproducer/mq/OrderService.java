package com.huawei.direct.springbootrabbitmqdirectproducer.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author xixi
 * @Description： 订单服务
 * @create 2020/3/22
 * @since 1.0.0
 */
@Component
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private AmqpTemplate amqpTemplate;

    @Value("${order.fanout.exchange}")
    private String exchange_name;


    public void sendMessage(){
        String orderId = UUID.randomUUID().toString();
        String msg = "你的订单消息：" + orderId + "，日期：" + new Date();
        System.out.println("[Producer OrderService] send msg:" + msg);

        rabbitTemplate.convertAndSend(exchange_name, "mail", msg);
        rabbitTemplate.convertAndSend(exchange_name, "log", msg);
        rabbitTemplate.convertAndSend(exchange_name, "weixin", msg);
    }
}
