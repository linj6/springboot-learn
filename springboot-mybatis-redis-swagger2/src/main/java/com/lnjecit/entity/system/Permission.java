package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by lnj
 * create time: 2018-05-17 21:26:59
 */
@ApiModel(value = "权限", description = "权限")
@Getter
@Setter
@ToString
public class Permission extends BaseEntity {

    @ApiModelProperty(value = "上级id", name = "parentId")
    private Long parentId;//上级id

    @ApiModelProperty(value = "名称", name = "username", required = true)
    @NotBlank(message = "名称不能为空")
    @Length(max = 32, message = "名称最大长度为32位")
    private String name;//名称

    @ApiModelProperty(value = "权限标识", name = "permissions")
    @Length(max = 500, message = "权限标识最大长度为500位")
    private String permissions;//权限标识

    @ApiModelProperty(value = "权限标识", name = "permissions")
    @Length(max = 500, message = "权限标识最大长度为500位")
    private String icon;//图标

    @ApiModelProperty(value = "url地址", name = "url")
    @Length(max = 255, message = "url地址最大长度为255位")
    private String url;//url地址

    @ApiModelProperty(value = "排序", name = "sort", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;//排序

    @ApiModelProperty(value = "类型 1:目录,2:菜单,3:按钮", name = "sort", required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;//类型(1:目录,2:菜单,3:按钮)）

    @ApiModelProperty(value = "状态 1:启用 0:不启用", name = "status", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;//状态 1:启用 0:不启用

    @ApiModelProperty(value = "下级权限集合", name = "subPermissions", hidden = true)
    private List<Permission> subPermissions = new ArrayList<>();

}
