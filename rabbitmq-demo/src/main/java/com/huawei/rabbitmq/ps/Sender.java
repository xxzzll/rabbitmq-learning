package com.huawei.rabbitmq.ps;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/21
 * @since 1.0.0
 */
public class Sender {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        // 声明交换机： exchange
        // durable = true 交换机的持久化
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

        String msg = "hello publish/subcribe pattern";
        // 消息的持久化设置：MessageProperties.PERSISTENT_TEXT_PLAIN
        channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());

        System.out.println("[producer] send:" + msg);
        channel.close();
        connection.close();
    }
}
