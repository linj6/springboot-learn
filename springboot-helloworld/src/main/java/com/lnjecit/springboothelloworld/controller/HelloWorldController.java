package com.lnjecit.springboothelloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2018-04-16 16:06
 **/
@RequestMapping("/helloWorld")
@RestController
public class HelloWorldController {

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam("name") String name) {
        return "Hi, " + name;
    }
}
