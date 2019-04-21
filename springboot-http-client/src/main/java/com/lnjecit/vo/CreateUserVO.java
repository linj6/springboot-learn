package com.lnjecit.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lnj
 * @description 创建用户VO
 * @date 2019-04-20 22:36
 **/
@Data
public class CreateUserVO {
    @NotBlank(message = "username 不能为空")
    private String username;
}
