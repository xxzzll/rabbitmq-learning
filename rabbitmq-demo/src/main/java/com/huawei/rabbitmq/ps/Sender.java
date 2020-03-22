package com.huawei.rabbitmq.ps;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

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
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String msg = "hello publish/subcribe pattern";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        System.out.println("[producer] send:" + msg);
        channel.close();
        connection.close();
    }
}
