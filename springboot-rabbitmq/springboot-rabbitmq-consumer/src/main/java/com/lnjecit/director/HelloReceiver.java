package com.lnjecit.director;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听hello队列
 *
 * @author lnj
 * createTime 2018-11-20 23:02
 **/
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("helloReceiver 接收消息：" + message);
    }

}
