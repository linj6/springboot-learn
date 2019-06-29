package com.lnjecit.distributed.lock.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RedisLockUtilTest {

    private final static Jedis JEDIS;

    static {
        JEDIS = new Jedis("127.0.0.1", 6379);
        JEDIS.auth("123456");
    }

    @Test
    public void tryGetDistributedLockTest() throws Exception {
        String key = "testDistributedLockKey";
        String requestId = UUID.randomUUID().toString();
        boolean getDistributedLockResult = RedisLockUtil.tryGetDistributedLock(JEDIS, key, requestId, 60 * 1000 * 60);
        System.out.println("getDistributedLockResult=" + getDistributedLockResult);
        Assert.assertTrue(getDistributedLockResult);

    }

    @Test
    public void releaseDistributedLockTest() throws Exception {
        String key = "testDistributedLockKey";
        String requestId = UUID.randomUUID().toString();

        boolean releaseDistributedLockResult = RedisLockUtil.releaseDistributedLock(JEDIS, key, requestId);
        System.out.println("releaseDistributedLockResult=" + releaseDistributedLockResult);
        Assert.assertTrue(releaseDistributedLockResult);
    }
}
