package com.imooc.exception;

import com.imooc.enums.ResultEnum;

/**
 * 自定义异常
 */
public class GirlException extends RuntimeException {

    private int code;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
