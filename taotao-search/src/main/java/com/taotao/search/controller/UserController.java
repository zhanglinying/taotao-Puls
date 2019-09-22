package com.taotao.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taotao.common.utils.CookieUtils;
import com.taotao.search.service.ApiService;
import com.taotao.search.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private JedisClient jedisClient;

    private static final String URL = "http://sso.taotao.com";

    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    @ResponseBody
    public Map toUser(@PathVariable(value = "token") String token) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String json = this.apiService.doGet(URL + "/service/user/" + token);
            System.out.println(json + "-----------");
            String jsonData = json.replaceAll("\\\\", "");
            String jsonData2 = jsonData.substring(1, jsonData.length() - 1);
            JSONObject jsonObject = JSON.parseObject(jsonData2);
            String username = (String) jsonObject.get("username");
            System.out.println(username);
            result.put("username", username);
        } catch (Exception e) {
            System.out.println("用户未登录...");
        }
        return result;
    }

    @RequestMapping(value = "/loginOut",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        String tt_cookie = CookieUtils.getCookieValue(request, "TT_COOKIE");
        System.out.println(tt_cookie);
        this.jedisClient.del("TOKEN_"+tt_cookie);
        return "index";
    }
}