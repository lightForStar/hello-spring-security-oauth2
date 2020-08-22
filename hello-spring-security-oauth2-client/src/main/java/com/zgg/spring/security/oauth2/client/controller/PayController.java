package com.zgg.spring.security.oauth2.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : Z先生
 * @date : 2020-08-22 15:19
 **/
@Controller
public class PayController {
    @GetMapping("/page/pay")
    public String toPagePay(){
        return "pay";
    }
}
