package com.lnjecit.fileupload.util;

/**
 * Result 返回结果封装
 *
 * @author lnj
 * @create 2018-03-02 10:16
 **/
public class Result {

    private int status;
    private String info;
    private Object data;

    public Result(ResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.status = resultCode.getStatus();
        this.info = resultCode.getInfo();
    }

    public Result(int status, String info, Object data) {
        this.status = status;
        this.info = info;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success() {
        return success(null);
    }

    public static Result success(String info) {
        return Result.success(info, null);
    }

    public static Result success(String info, Object data) {
        return new Result(ResultCode.SUCCESS.getStatus(), info, data);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS.getStatus(), ResultCode.SUCCESS.getInfo(), data);
    }

    public static Result error() {
        return error(ResultCode.ERROR, null);
    }

    public static Result error(ResultCode code) {
        return error(code, null);
    }

    public static Result error(String info) {
        return new Result(ResultCode.ERROR.getStatus(), info, null);
    }

    public static Result error(ResultCode code, Object object) {
        return new Result(code.getStatus(), code.getInfo(), object);
    }

}