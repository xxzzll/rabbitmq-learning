package com.huawei.direct.springbootrabbitmqdirectproducer.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author xixi
 * @Description： 用户服务
 * @create 2020/3/22
 * @since 1.0.0
 */
@Component
public class UserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private AmqpTemplate amqpTemplate;

    @Value("${order.fanout.exchange}")
    private String exchange_name;


    public void sendMessage(){
        String userId = "100";
        String msg = "用户ID：" + userId + "，日期：" + new Date();
        System.out.println("[Producer UserService] send msg:" + msg);

        rabbitTemplate.convertAndSend(exchange_name, "mail", msg);
        rabbitTemplate.convertAndSend(exchange_name, "log", msg);
    }
}
