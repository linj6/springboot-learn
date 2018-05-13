package com.lnjecit.generator.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 生成代码参数
 *
 * @author lnj
 * @create 2018-05-13 10:41
 **/
@ApiModel(value = "代码生成参数", description = "代码生成参数")
@Getter
@Setter
@ToString
public class GeneratorParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表名", name = "tableName", required = true)
    @NotBlank(message = "表名不能为空")
    private String tableName;//表名

    @ApiModelProperty(value = "模块名", name = "moduleName")
    private String moduleName;//模块名

    @ApiModelProperty(value = "是否带包名 1:是 0:否 默认为0")
    private Integer withPackage = 0;//是否带包名

}
