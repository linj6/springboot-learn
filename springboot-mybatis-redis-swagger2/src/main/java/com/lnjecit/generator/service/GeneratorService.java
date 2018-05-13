package com.lnjecit.generator.service;

import com.lnjecit.generator.entity.GeneratorParam;
import com.lnjecit.generator.entity.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GeneratorService {

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
     * 生成代码
     *
     * @param generatorParam 参数
     */
    byte[] generateCode(GeneratorParam generatorParam);
}
