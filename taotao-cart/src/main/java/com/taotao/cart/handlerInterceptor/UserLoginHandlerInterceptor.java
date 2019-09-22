package com.taotao.cart.handlerInterceptor;

import com.taotao.cart.pojo.tb_user;
import com.taotao.cart.service.UserService;
import com.taotao.cart.threadlocal.UserThreadlocal;
import com.taotao.common.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginHandlerInterceptor implements HandlerInterceptor {

    public static final String COOKIE_NAME = "TT_COOKIE";

    public static final String LOGIN_URL = "http://sso.taotao.com/login.html";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserThreadlocal.set(null);
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        if (StringUtils.isEmpty(token)) {
            //未登录状态
            response.sendRedirect(LOGIN_URL);
            return false;
        }
        tb_user user = this.userService.queryUserByToken(token);
        if(null==user){
            //未登录状态
            response.sendRedirect(LOGIN_URL);
            return false;
        }
        //处于登录状态
        UserThreadlocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}