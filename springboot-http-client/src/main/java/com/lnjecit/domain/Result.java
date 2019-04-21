package com.lnjecit.domain;

import lombok.Data;

/**
 * http请求最外层返回结果
 */
@Data
public class Result<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

}
