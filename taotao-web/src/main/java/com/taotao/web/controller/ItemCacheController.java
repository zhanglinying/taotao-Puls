package com.taotao.web.controller;
/**
 * @author : 小浪浪
 * @date : 10:21 2019/8/27
 */

import com.taotao.web.service.ItemCacheService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 小浪浪
 * @date 2019/8/27 10:21
 */
@Controller
@RequestMapping("item/cache")
public class ItemCacheController {


    @Autowired
    ItemCacheService itemCacheService;

    /**
     * 删除redis中的商品缓存数据
     *
     * @param itemId
     * @return
     */
    @RequestMapping("{itemId}")
    public ResponseEntity<Void> deleteRedisCache(@PathVariable(value = "itemId") Long itemId) {
        try {
            //String key="TAOTAO_WEB_ITEM_DESC605616";
            String key= ItemService.REDIS_KEY+itemId;
            System.out.println(key);
            itemCacheService.del(key);
            //this.jedisClient.del(key);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}