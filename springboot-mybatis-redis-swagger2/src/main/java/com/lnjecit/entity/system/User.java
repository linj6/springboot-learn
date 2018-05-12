package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * author name: lnj
 * create time: 2018-03-02 14:58:11
 */
@ApiModel(value = "用户对象", description = "用户对象user")
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名", name = "username", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

	@ApiModelProperty(value = "手机号", name = "mobile", required = true)
	@NotBlank(message = "手机号码不能为空")
	private String mobile;//手机号码

    @ApiModelProperty(value = "密码", name = "password", required = true)
	@NotBlank(message = "密码不能为空")
	private String password;//密码

}
