package com.zgg.spring.security.oauth2.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Z先生
 * @date : 2020-08-22 15:42
 **/
@RestController
public class TestController {


    @GetMapping("test")
    public String test(){
        return "test";
    }
}
