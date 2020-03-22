package com.huawei.fanout.springbootrabbitmqfanoutproducer;

import com.huawei.fanout.springbootrabbitmqfanoutproducer.mq.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqFanoutProducerApplicationTests {

    @Autowired
    private Producer producer;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            try {
                producer.sendMessage();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
