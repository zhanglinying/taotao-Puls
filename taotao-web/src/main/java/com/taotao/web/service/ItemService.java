package com.taotao.web.service;/**
 * @author : 小浪浪
 * @date : 12:28 2019/8/20
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.web.bean.Item;
import com.taotao.web.bean.ItemDesc;
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
                return OBJECT_MAPPER.readValue(cacheData,Item.class);
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

    /**
     * 查询商品详情
     *
     * @param itemId
     * @return
     */
    public ItemDesc queryItemDescByItemId(Long itemId) {
        String key=REDIS_KEY_DESC+itemId;
        try {
            if(jedisClient.exists(key)){
                String cacheData = this.jedisClient.get(key);
                System.out.println(cacheData);
                return OBJECT_MAPPER.readValue(cacheData,ItemDesc.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String url = TAOTAO_MANAGER_URL + "/rest/item/desc/" + itemId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            this.jedisClient.setAndExpire(key,jsonData,REDIS_TIME);
            return OBJECT_MAPPER.readValue(jsonData, ItemDesc.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询商品规格数据
     *
     * @param itemId
     * @return
     */
    public String queryItemParamByItemId(Long itemId) {
        try {
            String url = TAOTAO_MANAGER_URL + "/rest/item/param/" + itemId;
            String jsonData = this.apiService.doGet(url);
            JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonData);
            ArrayNode paramData = (ArrayNode) OBJECT_MAPPER.readTree(jsonNode.get("paramData").asText());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\" ><tbody>");
            for (JsonNode param : paramData) {
                stringBuilder.append("<tr><th class=\"tdTitle\" clospan=\"2\">" + param.get("group").asText() + "</th></tr>");
                ArrayNode params = (ArrayNode) param.get("params");
                System.out.println(params);
                for (JsonNode p : params) {
                    stringBuilder.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>" + p.get("v").asText() + "</td></tr>");
                }
            }
            stringBuilder.append("</tbody></table>");
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}