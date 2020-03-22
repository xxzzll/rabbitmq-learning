package com.huawei.rabbitmq.workfair;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 3、公平分发模式（fair dispatch）
 *                          |----consumer1
 * producer -----> queue--->|
 *                          |----consumer2
 * 设置：
 *  [生产者消费者都要设置]： channel.basicQos(1); 保证消息没有手动回执前只发送一条消息
 *  [仅消费者需要设置]：channel.basicAck(envelope.getDeliveryTag(), false); 手动回执
 *
 * 特点：
 *  能者多劳;
 *
 * @author xixi
 * @Description： 生产者
 * @create 2020/3/21
 * @since 1.0.0
 */
public class Sender {

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connetion.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for (int i = 0; i < 50; i++) {
            String msg = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("消息" + i + ",发布完成!");
            }
        }

        // 关闭资源
        channel.close();
        connetion.close();

    }
}
