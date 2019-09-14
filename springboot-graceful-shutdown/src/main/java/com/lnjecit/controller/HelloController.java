package com.lnjecit.controller;

import com.lnjecit.async.AsyncSaveLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class HelloController {

    @Autowired
    private AsyncSaveLog asyncSaveLog;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("req.........");
        try {
            asyncSaveLog.saveLog();
            Thread.sleep(1000 * 60 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }

}
