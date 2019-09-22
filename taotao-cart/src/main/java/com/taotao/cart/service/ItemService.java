package com.taotao.cart.service;/**
 * @author : 小浪浪
 * @date : 12:28 2019/8/20
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.cart.pojo.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 小浪浪
 * @date 2019/8/20 12:28
 */
@Service
public class ItemService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private JedisClient jedisClient;

    @Value("http://manager.taotao.com")
    private String TAOTAO_MANAGER_URL;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String REDIS_KEY = "TAOTAO_WEB_ITEM_DETALL";

    private static final String REDIS_KEY_DESC="TAOTAO_WEB_ITEM_DESC";

    private static final String REDIS_KEY_PARAM="TAOTAO_WEB_ITEM_PARAM";

    private static final Integer REDIS_TIME = 60 * 60 * 24;

    /**
     * 查询商品信息
     *
     * @param itemId
     * @return
     */
    public Item queryItemByItemId(Long itemId) {
        String key=REDIS_KEY+itemId;
        try {
            if(jedisClient.exists(key)){
                String cacheData = this.jedisClient.get(key);
                String data = cacheData.replaceAll(cacheData, "\\\\");
                return OBJECT_MAPPER.readValue(data,Item.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String url = TAOTAO_MANAGER_URL + "/rest/item/" + itemId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            try {
                this.jedisClient.setAndExpire(key,jsonData,REDIS_TIME);
            }catch (Exception e){
                e.printStackTrace();
            }
            return OBJECT_MAPPER.readValue(jsonData, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}