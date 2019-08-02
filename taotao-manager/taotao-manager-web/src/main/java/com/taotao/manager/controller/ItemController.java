package com.taotao.manager.controller;

import com.taotao.manager.utils.UUIDUtils;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-01 15:35
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc")String desc){
        try {
            if(StringUtils.isEmpty(item.getTitle())){
                //响应400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            //保存商品基本数据
            this.itemService.saveItem(item,desc);
            //成功201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        //出错500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
