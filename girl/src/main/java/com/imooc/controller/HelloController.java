package com.imooc.controller;

import com.imooc.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {


    @Autowired
    private GirlProperties girlProperties;

    @GetMapping(value = "say")
//    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String say(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
//        return girlProperties.getCupSize() + girlProperties.getAge();
//        return girlProperties.getContent();

        return "id:" + id;
    }
}
