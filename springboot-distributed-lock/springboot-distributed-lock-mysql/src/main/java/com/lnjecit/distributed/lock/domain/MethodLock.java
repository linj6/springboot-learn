package com.lnjecit.distributed.lock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MethodLock {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 锁定的方法名称
     */
    private String methodName;

    /**
     * 保存数据的时间
     */
    private Date updateTime;

}
