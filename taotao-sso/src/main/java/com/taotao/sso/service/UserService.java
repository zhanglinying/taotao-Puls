package com.taotao.sso.service;/**
 * @author : 小浪浪
 * @date : 11:02 2019/8/28
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.tb_user;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 小浪浪
 * @date 2019/8/28 11:02
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserMapper userMapper;

    private static final ObjectMapper OBJECT_MAPPR = new ObjectMapper();


    public Boolean check(String param, Integer type) {
        if (type < 1 || type > 3) {
            return null;
        }
        tb_user record = new tb_user();
        switch (type) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                break;
        }
        return this.userMapper.selectOne(record) == null;
    }


    public Boolean saveUser(tb_user user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        System.out.println(user.getPassword());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return this.userMapper.insertSelective(user) == 1;
    }

    public String queryLogin(String username, String password) {
        tb_user record = new tb_user();
        record.setUsername(username);
        tb_user user = this.userMapper.selectOne(record);
        if (null == user) {
            return null;
        }
        //比对密码是否正确
        if (!StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())) {
            return null;
        }
        //生存token
        String token = DigestUtils.md5Hex(username);
        String key = "TOKEN_" + token;
        //登录成功!
        this.cacheService.setAndExpire(key, user, 60 * 30);
        //this.jedisClient.setAndExpire("TOKEN_"+token,user,60*30);
        return token;
    }




    public tb_user queryUserByToken(String token) {
        String key = "TOKEN_" + token;
        String jsonData = this.jedisClient.get(key);
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }
        try {
            //刷新用户的生存时间
            this.jedisClient.expire(key, 60 * 30);
            return OBJECT_MAPPR.readValue(jsonData, tb_user.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}