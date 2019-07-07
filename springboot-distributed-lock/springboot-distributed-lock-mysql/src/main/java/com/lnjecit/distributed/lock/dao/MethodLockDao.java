package com.lnjecit.distributed.lock.dao;

import com.lnjecit.distributed.lock.domain.MethodLock;

public interface MethodLockDao {
    int insert(MethodLock methodLock);

    int deleteByMethodName(String methodName);
}
