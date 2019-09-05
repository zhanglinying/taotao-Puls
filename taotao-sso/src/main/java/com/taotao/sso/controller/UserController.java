package com.taotao.sso.controller;
/**
 * @author : 小浪浪
 * @date : 9:20 2019/8/28
 */

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.tb_user;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小浪浪
 * @date 2019/8/28 9:20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final String COOKIE_NAME = "TT_COOKIE";


    /**
     * 校验用户输入数据是否可用
     *
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
                                         @PathVariable("type") Integer type) {
        try {
            Boolean bool = this.userService.check(param, type);
            if (null == bool) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@Valid tb_user user, BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            //校验有错误
            List<String> list = new ArrayList<>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
            result.put("status", "400");
            result.put("data", StringUtils.join(list, '|'));
            return result;
        }
        try {
            Boolean boll = this.userService.saveUser(user);
            if (boll) {
                //注册成功
                result.put("status", "200");
            } else {
                //注册失败
                result.put("status", "300");
                result.put("data", "是的!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户登录验证
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "password") String password,
                                       HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        try {
            String token = this.userService.queryLogin(username, password);
            if (null == token) {
                //登录失败
                result.put("status", "400");
            } else {
                //登录成功,需要将token存入cooke中
                result.put("status", "200");
                CookieUtils.setCookie(request, response, COOKIE_NAME, token);
            }
        } catch (Exception e) {
            //登录失败
            result.put("status", "500");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    @ResponseBody
    public String queryUserByToken(@PathVariable(value = "token") String token){
        try {
            tb_user user = this.userService.queryUserByToken(token);
            if (null == user) {
                return null;
            }
            System.out.println(user.getUsername());
            String jsonString = JSON.toJSONString(user);
            System.out.println(jsonString+"====");
            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}