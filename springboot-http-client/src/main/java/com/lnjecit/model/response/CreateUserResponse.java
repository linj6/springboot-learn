package com.lnjecit.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author lnj
 * @description 创建用户请求的返回结果
 * @date 2019-04-20 22:28
 **/
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class CreateUserResponse extends AbstractResponse {
    private Long data;
}
