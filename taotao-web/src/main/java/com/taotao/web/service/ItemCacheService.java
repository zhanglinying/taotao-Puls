package com.taotao.web.service;/**
 * @author : 小浪浪
 * @date : 16:03 2019/8/27
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@author 小浪浪
 *@date 2019/8/27 16:03
 */
@Service
public class ItemCacheService {

    @Autowired
    JedisClient jedisClient;

    /**
     * 删除redis中的缓存key
     * @param key
     */
    public void del(String key) {
        long del = jedisClient.del(key);
        System.out.println(del);
    }
}