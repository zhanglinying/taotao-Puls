package com.taotao.manager.controller;

import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-07-30 22:24
 */
@Controller
@RequestMapping(value = "item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value = "id",defaultValue = "0")Long parentId) {
        try {
            ItemCat itemCat=new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> list =this.itemCatService.queryListByWhere(itemCat);
            if(null == list || list.isEmpty()){
                //资源不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
