package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Create by lnj
 * create time: 2018-05-15 21:21:00
 */
@ApiModel(value = "", description = "")
@Getter
@Setter
@ToString
public class Role extends BaseEntity {

    private String name;//角色名称

}
