package com.huawei.fanout.springbootrabbitmqdirectconsumer.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/22
 * @since 1.0.0
 */
@Component
// 1、把申请的队列与交换机绑定
// 2、确定消息的模式：fanout、direct、topic
// 3、确定队列的持久性： autoDelete
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${order.fanout.email.queue}", autoDelete = "true"),
        exchange = @Exchange(value = "${order.fanout.exchange}", type = ExchangeTypes.FANOUT),
        key = ""
))
public class EmailService {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("Email--------------->" + message);
    }
}
