package com.lnjecit.generator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 数据库表对应的java类信息
 *
 * @author lnj
 * @create 2018-05-13 16:03
 **/
@Getter
@Setter
@ToString
public class ClazzInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String className;//类名

    private String entityName;//对象名
}
