package com.lnjecit.generator.dao;

import com.lnjecit.generator.entity.Column;
import com.lnjecit.generator.entity.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GeneratorDao {

    /**
     * 获取当前数据库中所有表信息
     *
     * @param params 查询参数
     * @return
     */
    List<Table> getTableList(Map<String, Object> params);

    /**
     * 通过表名查询表信息
     *
     * @param tableName 表名
     * @return
     */
    Table getTableByName(@Param("tableName") String tableName);

    /**
     * 通过表名查询表中列信息
     *
     * @param tableName 表名
     * @return
     */
    List<Column> getColumnListByTableName(@Param("tableName") String tableName);
}
