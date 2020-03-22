package com.huawei.rabbitmq.spring;

/**
 * @author xixi
 * @Description： 消息消费者
 * @create 2020/3/22
 * @since 1.0.0
 */
public class MyCustomer {

    public void listen(String msg) {
        System.out.println("------------------[消费者] 消费的消息：" + msg);
    }
}
