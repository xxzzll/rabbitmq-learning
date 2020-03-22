package com.huawei.rabbitmq.simple;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 1、简单队列模式(Simple queues)
 *   producer ----> queue ----> consumer
 *
 * @author xixi
 * @Description： rabbitmq 消息发送者
 * @create 2020/3/21
 * @since 1.0.0
 */
public class Sender {
    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection rabbitMQConnect = RabbitmqConnectionUtil.getRabbitMQConnect();
        // 在连接中创建通道
        Channel channel = rabbitMQConnect.createChannel();
        // 创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false,false, null);

        String msg = "hello simple!";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("---send msd:" + msg);
        channel.close();
        rabbitMQConnect.close();
    }
}
