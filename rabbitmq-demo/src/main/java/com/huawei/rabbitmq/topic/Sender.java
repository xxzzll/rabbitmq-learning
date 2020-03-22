package com.huawei.rabbitmq.topic;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description： Topic模式下的消息生产者
 * @create 2020/3/21
 * @since 1.0.0
 */
public class Sender {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg = "hello routing pattern!";

        String routingKey = "goods.delete"; // goods.add,goods.update,goods.delete,...
        channel.basicPublish(EXCHANGE_NAME,routingKey, null, msg.getBytes());

        System.out.println("[producer] send msg:" + msg);
        channel.close();
        connection.close();
    }
}
