package com.huawei.rabbitmq.workfair;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description： 消息消费者01
 * @create 2020/3/21
 * @since 1.0.0
 */
public class receiver01 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[receiver01] receive msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[receiver01] receive msg done.");

                    // 手动回执消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        boolean autoAck = false; // 关闭自动应答
        // 注册 consumer 到 channel
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
