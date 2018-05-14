package com.lnjecit.controller.system;

import com.lnjecit.common.base.BaseController;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.result.Result;
import com.lnjecit.common.shiro.ShiroUtils;
import com.lnjecit.entity.system.User;
import com.lnjecit.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *
 * @author lnj
 * @create 2018-05-14 10:50
 **/
@Api(value = "登录", description = "登录api")
@RestController
public class LoginController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            Subject currentUser = ShiroUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            currentUser.login(usernamePasswordToken);

            User user = (User) ShiroUtils.getSessionAttribute(Constants.SESSION_USER);
            userService.updateLastLoginInfo();

            Map<String, Object> resultMap = new HashMap();
            resultMap.put(Constants.SESSION_USER, user);

            return Result.success(resultMap);
        } catch (Exception e) {
            if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
                return Result.error(MsgConstants.USERNAME_OR_PASSWORD_ERROR);
            }
            LOGGER.error(e.getCause().getMessage(), e);
            return Result.error(e.getCause().getMessage());
        }
    }

}
