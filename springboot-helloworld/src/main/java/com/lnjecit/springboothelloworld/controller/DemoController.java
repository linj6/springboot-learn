package com.lnjecit.springboothelloworld.controller;

import com.lnjecit.service.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2018-04-16 16:06
 **/
@RequestMapping("/demo")
@RestController
public class DemoController {

    @Autowired
    private PrefixService prefixService;

    @GetMapping("/input")
    public String input(@RequestParam("word") String word) {
        return prefixService.wrap(word);
    }
}
