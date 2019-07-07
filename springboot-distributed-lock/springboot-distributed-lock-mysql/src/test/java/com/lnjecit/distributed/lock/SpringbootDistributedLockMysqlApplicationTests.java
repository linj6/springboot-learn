package com.lnjecit.distributed.lock;

import com.lnjecit.distributed.lock.mysql.MySqlLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDistributedLockMysqlApplicationTests {

    @Autowired
    private MySqlLock mySqlLock;

    @Test
    public void mysqlLockTest() throws IOException {
        final String methodName = "testDistributedLockKey";
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    mySqlLock.tryGetDistributedLock(methodName);
                    System.out.println(Thread.currentThread().getName() + "进入了临界区");
                    Thread.sleep(2000);
                    mySqlLock.releaseDistributedLock(methodName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.in.read();
    }

}
