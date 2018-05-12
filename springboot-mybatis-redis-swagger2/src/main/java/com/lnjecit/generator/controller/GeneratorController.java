package com.lnjecit.generator.controller;

import com.lnjecit.generator.entity.TableEntity;
import com.lnjecit.generator.service.GeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api(value = "代码生成", description = "代码生成api")
@RequestMapping("/generator")
@RestController
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @ApiOperation(value = "获取数据库表", notes = "获取数据库表")
    @ApiImplicitParam(name = "tableName", value = "表名", dataType = "string", paramType = "query")
    @GetMapping("/getTableList")
    public List<TableEntity> getTableList(@RequestParam(required = false) @ApiIgnore Map<String, Object> params) {
        return generatorService.getTableList(params);
    }

}
