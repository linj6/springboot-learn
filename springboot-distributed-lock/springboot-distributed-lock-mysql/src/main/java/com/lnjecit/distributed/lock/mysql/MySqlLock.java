package com.lnjecit.distributed.lock.mysql;

import com.lnjecit.distributed.lock.dao.MethodLockDao;
import com.lnjecit.distributed.lock.domain.MethodLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Date;

@Slf4j
@Component
public class MySqlLock {

    @Autowired
    private MethodLockDao methodLockDao;

    public void tryGetDistributedLock(String methodName) {
        while (true) {
            try {
                MethodLock methodLock = new MethodLock();
                methodLock.setMethodName(methodName);
                methodLock.setUpdateTime(new Date());

                methodLockDao.insert(methodLock);
                log.info(Thread.currentThread().getName() + "成功获取锁");
                return;
            } catch (Exception ex) {
                try {
                    log.error(Thread.currentThread().getName() + "获取锁失败，重试中。。。");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("获取锁出错", e);
                }
            }
        }
    }

    public void releaseDistributedLock(String methodName) throws SQLException {
        log.info(Thread.currentThread().getName() + "开始释放锁");
        methodLockDao.deleteByMethodName(methodName);
        log.info(Thread.currentThread().getName() + "释放了锁");
    }
}
