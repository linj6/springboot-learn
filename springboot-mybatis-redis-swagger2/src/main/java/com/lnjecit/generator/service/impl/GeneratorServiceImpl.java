package com.lnjecit.generator.service.impl;

import com.lnjecit.generator.dao.GeneratorDao;
import com.lnjecit.generator.entity.Column;
import com.lnjecit.generator.entity.GeneratorParam;
import com.lnjecit.generator.entity.Table;
import com.lnjecit.generator.service.GeneratorService;
import com.lnjecit.generator.util.GeneratorUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private GeneratorDao generatorDao;

    @Override
    public List<Table> getTableList(Map<String, Object> params) {
        return generatorDao.getTableList(params);
    }

    @Override
    public Table getTableByName(String tableName) {
        return generatorDao.getTableByName(tableName);
    }

    @Override
    public byte[] generateCode(GeneratorParam generatorParam) {
        String tableName = generatorParam.getTableName();
        Table table = generatorDao.getTableByName(tableName);
        List<Column> columnList = generatorDao.getColumnListByTableName(tableName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(out);
        GeneratorUtil.generatorCode(table, columnList, generatorParam, zip);
        IOUtils.closeQuietly(zip);
        return out.toByteArray();
    }
}
