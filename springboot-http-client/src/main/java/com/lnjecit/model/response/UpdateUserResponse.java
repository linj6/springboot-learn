package com.lnjecit.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author lnj
 * @description 修改用户请求的返回结果
 * @date 2019-04-21 13:58
 **/
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UpdateUserResponse extends AbstractResponse {

}
