package com.huawei.rabbitmq.work;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 2、工作队列模式(Work queues)
 *                          |----consumer1
 * producer -----> queue--->|
 *                          |----consumer2
 *
 * 消费者01 和 消费者02 消费的消息总数一样,这种方式叫做轮询分发（round-robin）;
 * 不管谁忙或者谁闲都不会多分配给它消息。
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

        for (int i = 0; i < 50; i++) {
            String msg = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            try {
                Thread.sleep(1000);
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
