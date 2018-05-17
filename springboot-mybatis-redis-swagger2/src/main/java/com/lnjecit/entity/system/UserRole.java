package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Create by lnj
 * create time: 2018-05-15 22:08:15
 */
@ApiModel(value = "用户角色关系", description = "用户角色关系")
@Getter
@Setter
@ToString
public class UserRole extends BaseEntity {

    private Long userId;//用户id
    private Long roleId;//角色id

}
