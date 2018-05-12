package com.lnjecit.generator.service.impl;

import com.lnjecit.generator.dao.GeneratorDao;
import com.lnjecit.generator.entity.TableEntity;
import com.lnjecit.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private GeneratorDao generatorDao;

    @Override
    public List<TableEntity> getTableList(Map<String, Object> params) {
        return generatorDao.getTableList(params);
    }

    @Override
    public TableEntity getTableByName(String tableName) {
        return generatorDao.getTableByName(tableName);
    }
}
