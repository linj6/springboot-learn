package com.imooc.domain;

/**
 * http请求最外层返回结果
 *
 */
public class Result<T> {

    //错误码
    private int code;

    //提示信息
    private String msg;

    //数据
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
