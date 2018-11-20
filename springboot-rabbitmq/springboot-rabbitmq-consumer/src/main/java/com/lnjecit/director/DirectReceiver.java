package com.lnjecit.director;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听direct队列
 *
 * @author lnj
 * createTime 2018-11-20 23:06
 **/
@Component
@RabbitListener(queues = "direct")
public class DirectReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("directReceiver 接收消息：" + message);
    }
}
