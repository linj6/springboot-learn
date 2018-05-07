package com.lnjecit.controller.system;

import com.lnjecit.common.base.GenericBaseController;
import com.lnjecit.common.result.Result;
import com.lnjecit.entity.system.User;
import com.lnjecit.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author lnj
 * @create 2018-03-02 15:09
 **/
@Api(value = "用户类", description = "用户api")
@RequestMapping("/user")
@RestController
public class UserController extends GenericBaseController<UserService, User> {

    @Override
    protected void beforeSave(User entity) {
        super.beforeSave(entity);
    }

    @Override
    protected void entityAfter(User entity) {
        super.entityAfter(entity);
        entity.setPassword("");
    }

    @ApiOperation(value = "注册", notes = "注册")
    @ResponseBody
    @PostMapping("register")
    public Result register() {
        return Result.success("Register success");
    }
}
