package com.lnjecit.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author lnj
 * @description 异步保存日志
 * @date 2019-09-14 16:11
 **/
@EnableAsync
@Component
public class AsyncSaveLog {

    @Async
    public void saveLog() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000 * 60 * 2 + 1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
