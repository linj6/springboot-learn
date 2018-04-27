package com.lnjecit.controller.system;

import com.lnjecit.common.base.BaseController;
import com.lnjecit.entity.system.User;
import com.lnjecit.service.system.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author lnj
 * @create 2018-03-02 15:09
 **/
@Api(value = "用户类", description = "用户api")
@RequestMapping("/user")
@RestController
public class UserController extends BaseController<UserService, User> {

    @Override
    protected void beforeSave(User entity) {
        super.beforeSave(entity);
    }

    @Override
    protected void entityAfter(User entity) {
        super.entityAfter(entity);
        entity.setPassword("");
    }
}
