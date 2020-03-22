package com.huawei.rabbitmq.routing;

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
public class receiver02 {
    private static final String QUEUE_NAME = "test_queue_direct_2";
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        // 声明消息队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 将交换机（exhange）和消息队列（queue）相绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[receiver02] receive msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[receiver02] receive msg done.");

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
