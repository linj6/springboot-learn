package com.lnjecit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lnjecit.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lnj
 * createTime 2018-11-05 21:09
 **/
@RequestMapping("user")
@RestController
public class UserController {
    @Reference
    private UserService userService;

    @GetMapping("list")
    public List<String> list() {
        return userService.query();
    }
}
