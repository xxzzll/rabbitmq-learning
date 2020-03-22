package com.huawei.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description： rabbitmq 工具类
 * @create 2020/3/21
 * @since 1.0.0
 */
public class RabbitmqConnectionUtil {

    public static Connection getRabbitMQConnect() throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 服务器地址
        factory.setHost("127.0.0.1");

        // AMQP 5762
        factory.setPort(5672);

        // vhost
        factory.setVirtualHost("/test");

        factory.setUsername("test");
        factory.setPassword("test");


        return factory.newConnection();
    }
}
