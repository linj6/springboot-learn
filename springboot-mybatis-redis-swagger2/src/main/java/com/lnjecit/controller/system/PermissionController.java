package com.lnjecit.controller.system;

import com.github.pagehelper.PageInfo;
import com.lnjecit.common.aspect.Log;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.page.PageBuiler;
import com.lnjecit.common.result.Result;
import com.lnjecit.entity.system.Permission;
import com.lnjecit.service.system.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "权限", description = "权限api")
@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取详情", notes = "根据id来获取详情")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @GetMapping("getById")
    protected Result getById(@RequestParam("id") Long id) {
        return Result.success(permissionService.getById(id));
    }

    @ApiOperation(value = "列表查询（分页）", notes = "列表查询（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, defaultValue = "10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "int", paramType = "query", example = "updateTime desc, id asc"),
            @ApiImplicitParam(name = "name", value = "权限名称", dataType = "String", paramType = "query"),
    })
    @ResponseBody
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) @ApiIgnore Map<String, Object> params) {
        PageInfo<?> pageInfo = PageBuiler.builder(params);
        return permissionService.queryPage(params, pageInfo);
    }

    @ApiOperation(value = "以树形结构返回权限列表", notes = "以树形结构返回权限列表")
    @ResponseBody
    @GetMapping("/listTree")
    public Result listTree() {
        List<Permission> permissionList = permissionService.getPermissionTreeList();
        Map<String, List<Permission>> resultMap = new HashMap<>();
        resultMap.put(Constants.LIST, permissionList);
        return Result.success(resultMap);
    }

    @Log(description = "新增权限")
    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Permission permission, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return permissionService.insert(permission);
    }

    @Log(description = "修改权限")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PostMapping("/update")
    public Result update(@Valid @RequestBody Permission permission, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return permissionService.update(permission);
    }

    @Log(description = "删除权限")
    @ApiOperation(value = "删除", notes = "根据id来删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        return permissionService.deleteLogical(id);
    }

    @Log(description = "批量删除权限")
    @ApiOperation(value = "批量删除", notes = "根据id来批量删除")
    @ApiImplicitParam(name = "ids", value = "ids", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam(name = "ids") Long[] ids) {
        return permissionService.deleteLogicalBatch(ids);
    }

}
