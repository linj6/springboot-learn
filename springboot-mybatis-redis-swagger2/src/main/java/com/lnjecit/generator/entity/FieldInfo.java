package com.lnjecit.generator.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 数据库表列对应的java实体字段信息
 *
 * @author lnj
 * @create 2018-05-13 11:05
 **/
@ApiModel(value = "数据库表列对应的java实体字段信息", description = "数据库表列对应的java实体字段信息")
@Getter
@Setter
@ToString
public class FieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fieldName;//字段名
    private String fieldType;//字段数据类型
    private String fieldComment;//字段注释

}
