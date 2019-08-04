package com.taotao.manager.controller;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.pojo.Item;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //输出日志信息
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc) {
        LOGGER.debug("新增商品，item={}，desc={}" + item, desc);
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                //响应400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            //保存商品基本数据
            this.itemService.saveItem(item, desc);
            LOGGER.debug("新增商品成功!,itemId={}", item.getId());
            //成功201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("新增商品失败! title:" + item.getTitle() + "id:" + item.getId(), e);
            e.printStackTrace();
        }
        //出错500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 功能:<查询商品列表>
     *
     * @param page
     * @param rows
     * @return {@link ResponseEntity< EasyUIResult>}
     * @author
     * @date 2019-08-03 10:51:12
     **/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            PageInfo<Item> itemPageInfo = this.itemService.queryPageList(page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(itemPageInfo.getTotal(), itemPageInfo.getList());
            //成功200
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //出错500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}