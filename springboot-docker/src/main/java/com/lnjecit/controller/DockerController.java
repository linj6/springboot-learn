package com.lnjecit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lnj
 * createTime 2018-12-05 20:19
 **/
@RequestMapping("/docker")
@RestController
public class DockerController {

    @GetMapping("index")
    public String index() {
        return "springboot docker index";
    }
}
