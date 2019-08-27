package com.taotao.service.redis.impl;/**
 * @author : 小浪浪
 * @date : 9:30 2019/8/22
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.service.redis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.List;

/**
 * @author 小浪浪
 * @date 2019/8/22 9:30
 */
@Service
public class JedisClientCluster implements JedisClient {

    @Autowired
    private JedisCluster jedisCluster;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public <T> T get(String key, TypeReference<T> clazz) {

        String json = jedisCluster.get(key);

        if (StringUtils.isNotEmpty(json)) {

            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public <T> T get(String key, Class<T> clazz) {

        String json = jedisCluster.get(key);

        if (StringUtils.isNotEmpty(json)) {

            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public void set(String key, Object o) {

        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jedisCluster.set(key, json);

    }


    public void setAndExpire(String key, Object o, int expire) {

        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jedisCluster.set(key, json);

        jedisCluster.expire(key, expire);

    }


// public long del(String key) {

// return jedisCluster.del(key);

// }


    public String get(String key) {

        return jedisCluster.get(key);

    }

    @Override

    public String set(String key, String value) {

        return jedisCluster.set(key, value);

    }

    @Override

    public String hget(String hkey, String key) {

        return jedisCluster.hget(hkey, key);

    }


    @Override

    public long hset(String hkey, String key, String value) {

        return jedisCluster.hset(hkey, key, value);

    }


    @Override

    public long incr(String key) {

        return jedisCluster.incr(key);

    }


    public Long decr(String key) {

        return jedisCluster.decr(key);

    }


    @Override

    public long expire(String key, int second) {

        return jedisCluster.expire(key, second);

    }


    @Override

    public long ttl(String key) {

        return jedisCluster.ttl(key);

    }


    @Override

    public long del(String key) {

        return jedisCluster.del(key);

    }


    @Override

    public long hdel(String hkey, String key) {


        return jedisCluster.hdel(hkey, key);

    }


    public Long rpush(String key, String string) {

        return jedisCluster.rpush(key, string);

    }


    public Long lpush(String key, String string) {

        return jedisCluster.lpush(key, string);

    }


    public Boolean exists(String key) {

        return jedisCluster.exists(key);

    }


    public List<String> brpop(int timeout, String key) {

        return jedisCluster.brpop(timeout, key);

    }
}