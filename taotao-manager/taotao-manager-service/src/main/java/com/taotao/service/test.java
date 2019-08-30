package com.taotao.service;/**
 * @author : 小浪浪
 * @date : 17:23 2019/8/16
 */

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 *@author 小浪浪
 *@date 2019/8/16 17:23
 */
@Service
public class test {


    @Test
    public void test1(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("193.112.217.168",6381));
        nodes.add(new HostAndPort("193.112.217.168",6382));
        nodes.add(new HostAndPort("193.112.217.168",6383));
        nodes.add(new HostAndPort("193.112.217.168",6384));
        nodes.add(new HostAndPort("193.112.217.168",6385));
        nodes.add(new HostAndPort("193.112.217.168",6386));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //jedisCluster.set("name","张三");
        jedisCluster.del("TAOTAO_WEB_ITEM_DETALL605616");
        //System.out.println(name);
    }
}