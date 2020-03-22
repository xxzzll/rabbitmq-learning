package com.huawei.rabbitmq.comfirm;

import com.huawei.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @author xixi
 * @Description： 异步确认发送的消息
 * @create 2020/3/22
 * @since 1.0.0
 */
public class AsynchronousSender {
    private static final String QUEUE_NAME = "test_queue_confirm_3";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitmqConnectionUtil.getRabbitMQConnect();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 开启 confirm 模式
        channel.confirmSelect();

        // 容纳：消息序号
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        // 添加监听
        channel.addConfirmListener(new ConfirmListener() {
            // 处理已确定的消息
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("------------handleAck-----multiple true");
                    confirmSet.headSet(deliveryTag + 1).clear();
                }else{
                    System.out.println("------------handleAck-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

            // 处理未确定的消息
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("------------handleNack-----multiple true");
                    confirmSet.headSet(deliveryTag + 1).clear();
                }else{
                    System.out.println("------------handleNack-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "send a message to be acknowledged asynchronously";

        while (true){
            // sequence number: a number that identifies the confirmed or nack-ed message.
            long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(nextPublishSeqNo);
        }

    }
}
