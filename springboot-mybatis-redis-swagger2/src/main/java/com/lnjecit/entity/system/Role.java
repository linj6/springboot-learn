package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create by lnj
 * create time: 2018-05-15 21:21:00
 */
@ApiModel(value = "角色", description = "角色")
@Getter
@Setter
@ToString
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名称", name = "username", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 32, message = "角色名称最大长度为32位")
    private String name;//角色名称

}
