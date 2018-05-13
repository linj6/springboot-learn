package com.lnjecit.generator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 数据库表属性
 *
 * @author lnj
 * @create 2018-05-12 20:29
 **/
@Getter
@Setter
@ToString
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableSchema;//数据表所属的数据库名

    private String tableName;//表名

    private String tableComment;//表备注

    private String engine;//数据库引擎

    private Date createTime;//创建时间

    private List<Column> columnList;//数据库列集合

}
