package com.lnjecit.generator.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 数据库表列
 *
 * @author lnj
 * @create 2018-05-13 11:05
 **/
@ApiModel(value = "数据库表中列的属性", description = "数据库表中列的属性")
@Getter
@Setter
@ToString
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    private String columnName;//列名
    private String dataType;//列数据类型
    private String columnComment;//列注释
    private String columnKey;//列上的索引类型 主键-->PRI  | 唯一索引 -->UNI  一般索引 -->MUL
}
