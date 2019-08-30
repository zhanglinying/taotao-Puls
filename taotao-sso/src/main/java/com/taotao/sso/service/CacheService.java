package com.taotao.sso.service;/**
 * @author : 小浪浪
 * @date : 23:01 2019/8/29
 */

import com.taotao.sso.pojo.tb_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@author 小浪浪
 *@date 2019/8/29 23:01
 */
@Service
public class CacheService {


    @Autowired
    private JedisClient jedisClient;


    public void setAndExpire(String s, tb_user user, int i) {

        this.jedisClient.setAndExpire(s,user,i);
    }
}