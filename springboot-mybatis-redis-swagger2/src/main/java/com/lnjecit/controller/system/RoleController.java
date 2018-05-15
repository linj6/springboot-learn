package com.lnjecit.controller.system;

import com.github.pagehelper.PageInfo;
import com.lnjecit.common.page.PageBuiler;
import com.lnjecit.common.result.Result;
import com.lnjecit.entity.system.Role;
import com.lnjecit.service.system.RoleService;
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
import java.util.List;
import java.util.Map;

@Api(value = "角色", description = "角色api")
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取详情", notes = "根据id来获取详情")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @GetMapping("getById")
    protected Result getById(@RequestParam("id") Long id) {
        return Result.success(roleService.getById(id));
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
        return roleService.queryPage(params, pageInfo);
    }

    @ApiOperation(value = "列表查询（分页）", notes = "列表查询（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, defaultValue = "10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "int", paramType = "query", example = "updateTime desc, id asc"),
    })
    @ResponseBody
    @GetMapping("/listNoPage")
    public Result listNoPage(@RequestParam(required = false) @ApiIgnore Map<String, Object> params) {
        List<Role> roleList = roleService.query(params);
        return Result.success(roleList);
    }


    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return roleService.insert(role);
    }

    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PostMapping("/update")
    public Result update(@Valid @RequestBody Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return roleService.update(role);
    }

    @ApiOperation(value = "删除", notes = "根据id来删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        return roleService.deleteLogical(id);
    }

    @ApiOperation(value = "批量删除", notes = "根据id来批量删除")
    @ApiImplicitParam(name = "ids", value = "ids", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam(name = "ids") Long[] ids) {
        return roleService.deleteLogicalBatch(ids);
    }

}
