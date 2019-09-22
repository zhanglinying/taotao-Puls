package com.taotao.web.mq.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ItemMQHandler {

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    @Autowired
    private JedisClient jedisClient;

    public void Foo(String msg){
        System.out.println(msg+"================");
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(msg);
            String itemId = jsonNode.get("ItemId").asText();
            String key=ItemService.REDIS_KEY+itemId;
            this.jedisClient.del(key);
            this.jedisClient.del("TAOTAO_WEB_ITEM_DESC"+itemId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}