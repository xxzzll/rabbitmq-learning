package com.huawei.rabbitmq.simple;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description： rabbitmq 消息接收者
 * @create 2020/3/21
 * @since 1.0.0
 */
public class receiver {
    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        // Interface for application callback objects to receive notifications and messages from a queue by subscription.
        // 应用程序回调对象的接口用于接收通知和订阅队列中的消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("new api receiver msg:" + msg);
            }
        };

        // 注册 consumer 到 channel
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    /*
    private static void oldAPI() throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(QUEUE_NAME, true, consumer);
        while(true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("[receiver] msg:" + msg);
        }
    }
    */
}
