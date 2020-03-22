package com.huawei.direct.springbootrabbitmqdirectproducer;

import com.huawei.direct.springbootrabbitmqdirectproducer.mq.OrderService;
import com.huawei.direct.springbootrabbitmqdirectproducer.mq.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqFanoutProducerApplicationTests {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        orderService.sendMessage();
        userService.sendMessage();
    }

}
