package com.lnjecit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 默认交换机模式
 *
 * @author lnj
 * createTime 2018-11-20 23:10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqDirectTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendHelloTest() {
        String message = "此消息在，默认交换机模式(direct)队列下，有 helloReceiver 可以收到";

        String routeKey = "hello";

        message = "routeKey:" + routeKey + ",message:" + message;

        System.out.println("sendHelloTest : " + message);

        amqpTemplate.convertAndSend(routeKey, message);

    }

    @Test
    public void sendDirectTest() {
        String message = "此消息在默认交换机模式队列下，有 directReceiver 可以收到";

        String routeKey = "direct";

        message = "routeKey:" + routeKey + ",message:" + message;

        System.out.println("sendDirectTest:" + message);

        amqpTemplate.convertAndSend(routeKey, message);
    }

}
