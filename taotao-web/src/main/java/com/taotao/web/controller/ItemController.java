package com.taotao.web.controller;/**
 * @author : 小浪浪
 * @date : 12:24 2019/8/20
 */

import com.taotao.web.bean.Item;
import com.taotao.web.bean.ItemDesc;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 小浪浪
 * @date 2019/8/20 12:24
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("itemId")Long itemId){
        System.out.println(itemId);
        ModelAndView modelAndView = new ModelAndView("item");
        //商品数据
        Item item=this.itemService.queryItemByItemId(itemId);
        modelAndView.addObject("item",item);
        //商品详情数据
        ItemDesc itemDesc=this.itemService.queryItemDescByItemId(itemId);
        modelAndView.addObject("itemDesc",itemDesc);
        //商品规格数据
        String itemParam=this.itemService.queryItemParamByItemId(itemId);
        System.out.println(itemParam);
        modelAndView.addObject("itemParam",itemParam);
        return modelAndView;
    }
}