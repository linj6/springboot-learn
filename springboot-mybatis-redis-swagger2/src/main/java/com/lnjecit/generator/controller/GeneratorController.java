package com.lnjecit.generator.controller;

import com.lnjecit.generator.entity.GeneratorParam;
import com.lnjecit.generator.entity.Table;
import com.lnjecit.generator.service.GeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
    public List<Table> getTableList(@RequestParam(required = false) @ApiIgnore Map<String, Object> params) {
        return generatorService.getTableList(params);
    }

    @ApiOperation(value = "生成代码", notes = "生成代码")
    @RequestMapping(value = "/generateCode", method = {RequestMethod.GET, RequestMethod.POST})
    public void generateCode(@Valid GeneratorParam generatorParam, BindingResult bindingResult, HttpServletResponse response) throws IOException {
        //如果验证不通过
        if (bindingResult.hasErrors()) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println(bindingResult.getFieldError().getDefaultMessage());
        } else {
            byte[] code = generatorService.generateCode(generatorParam);
            String attachment = "attachment; filename=" + generatorParam.getTableName() + ".zip";
            response.reset();
            response.setHeader("Content-Disposition", attachment);
            response.addHeader("Content-Length", "" + code.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(code, response.getOutputStream());
        }
    }

}
