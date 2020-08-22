package com.zgg.spring.security.oauth2.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.zgg.spring.security.oauth2.client.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Z先生
 * @date : 2020-08-22 15:10
 **/
@RestController
public class TokenController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/code")
    public String getToken(String code){
        //用户同意授权才能拿到授权码
        System.out.println("获取到的code："+code);
        //根据授权码获取access_token，利用access_token操作用户资源
        Map<String,String> map = new HashMap<>();
        map.put("code",code);
        map.put("grant_type","authorization_code");

        JSONObject jsonObject = HttpClientUtil.PostBody("http://client:secret@localhost:8080/oauth/token", map, null, "UTF-8");

        System.out.println(jsonObject);

        return "success";
    }

}
