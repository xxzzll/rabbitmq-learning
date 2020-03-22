package com.huawei.rabbitmq.comfirm;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description： 单条确认发送的消息
 * @create 2020/3/22
 * @since 1.0.0
 */
public class SingleSender {
    private static final String QUEUE_NAME = "test_queue_confirm_1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 开启 confirm 模式
        channel.confirmSelect();

        String msg = "send a msg use confirm pattern";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        // wait for confirm operation
        if(channel.waitForConfirms()){
            System.out.println("[producer] send msg ok");
        }else{
            System.out.println("[producer] send msg fail");
        }

        channel.close();
        connection.close();
    }
}
