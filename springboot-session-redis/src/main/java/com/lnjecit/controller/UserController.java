package com.lnjecit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lnj
 * @description UserController
 * @date 2019-01-12 15:43
 **/
@RequestMapping("user")
@RestController
public class UserController {

    @GetMapping("getSessionId")
    public String getSessionId(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        System.out.println(sessionId);
        return sessionId;
    }
}
