package com.lnjecit.util;

import com.lnjecit.domain.Result;
import com.lnjecit.domain.ResultEnum;

/**
 * http请求返回结果工具类
 */
public class ResultUtil {

    /**
     * 请求成功
     *
     * @param data 返回数据
     * @return 请求成功结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 请求失败
     *
     * @param code    错误码
     * @param message 错误消息
     * @return 请求失败结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 请求失败
     *
     * @param resultEnum 返回结果枚举
     * @return 请求失败结果
     */
    public static <T> Result<T> error(ResultEnum resultEnum) {
        Result<T> result = new Result<>();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }
}
