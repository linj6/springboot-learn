package com.lnjecit.controller.system;

import com.github.pagehelper.PageInfo;
import com.lnjecit.common.base.BaseController;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.page.PageBuiler;
import com.lnjecit.common.result.Result;
import com.lnjecit.entity.system.User;
import com.lnjecit.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(value = "用户", description = "用户api")
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取详情", notes = "根据id来获取详情")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @GetMapping("getById")
    protected Result getById(@RequestParam("id") Long id) {
        User user = userService.getById(id);
        if (null != user) {
            user.setPassword("");
        }
        return Result.success(user);
    }

    @ApiOperation(value = "列表查询（分页）", notes = "列表查询（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, defaultValue = "10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "int", paramType = "query", example = "updateTime desc, id asc"),
    })
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) @ApiIgnore Map<String, Object> params) {
        PageInfo<?> pageInfo = PageBuiler.builder(params);
        Result result = userService.queryPage(params, pageInfo);
        Map<String, Object> data = (Map<String, Object>) result.getData();
        List<User> userList = (List<User>) data.get(Constants.LIST);
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                user.setPassword("");
            }
        }
        return result;
    }


    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/save")
    public Result save(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        user.setRegisterIp(getIpAddr(request));
        return userService.insert(user);
    }

    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/update")
    public Result update(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.update(user);
    }

    @ApiOperation(value = "删除", notes = "根据id来删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        return userService.deleteLogical(id);
    }

    @ApiOperation(value = "批量删除", notes = "根据id来批量删除")
    @ApiImplicitParam(name = "ids", value = "ids", required = true, allowMultiple = true, dataType = "Long", paramType = "query")
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam(name = "ids") Long[] ids) {
        return userService.deleteLogicalBatch(ids);
    }

}
