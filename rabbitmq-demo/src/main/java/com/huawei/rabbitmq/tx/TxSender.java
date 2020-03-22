package com.huawei.rabbitmq.tx;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/22
 * @since 1.0.0
 */
public class TxSender {

    private static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "send a msg with transcation";

        try {

            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            int i = 1/0; // 模拟异常的产生

            System.out.println("[producer] send msg:" + msg);
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();
            System.out.println("send msg txRollback");
        }

        channel.close();
        connection.close();

    }
}
