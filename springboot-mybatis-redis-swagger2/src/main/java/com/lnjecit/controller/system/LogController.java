package com.lnjecit.controller.system;

import com.github.pagehelper.PageInfo;
import com.lnjecit.common.page.PageBuiler;
import com.lnjecit.common.result.Result;
import com.lnjecit.service.system.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(value = "日志", description = "日志Api")
@RequestMapping("/log")
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "获取详情", notes = "根据id来获取详情")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @GetMapping("getById")
    protected Result getById(@RequestParam("id") Long id) {
        return Result.success(logService.getById(id));
    }

    @ApiOperation(value = "列表查询（分页）", notes = "列表查询（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, defaultValue = "10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "int", paramType = "query", example = "updateTime desc, id asc"),
    })
    @ResponseBody
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) @ApiIgnore Map<String, Object> params) {
        PageInfo<?> pageInfo = PageBuiler.builder(params);
        return logService.queryPage(params, pageInfo);
    }

}
