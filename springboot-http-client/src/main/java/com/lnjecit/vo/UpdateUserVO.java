package com.lnjecit.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author lnj
 * @description 修改用户VO
 * @date 2019-04-21 14:01
 **/
@Data
public class UpdateUserVO {
    @NotNull(message = "userId 不能为空")
    private Long userId;

    @NotBlank(message = "username 不能为空")
    private String username;
}
