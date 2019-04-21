package com.lnjecit.model.response;

import com.lnjecit.domain.ResultEnum;
import lombok.Data;

import java.util.Objects;

/**
 * @author lnj
 * @description AbstractResponse
 * @date 2019-04-21 11:10
 **/
@Data
public abstract class AbstractResponse {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 判断请求是否成功
     *
     * @return true: 成功 false: 失败
     */
    public Boolean isSuccess() {
        return Objects.equals(ResultEnum.SUCCESS.getCode(), code);
    }
}
