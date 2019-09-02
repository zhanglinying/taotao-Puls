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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 功能:<新增商品信息>
     *
     * @param item
     * @param desc
     * @return {@link ResponseEntity< Void>}
     * @author
     * @date 2019-08-05 14:23:26
     **/
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
     * 功能:<修改商品>
     *
     * @param item
     * @param desc
     * @return {@link ResponseEntity< Void>}
     * @author
     * @date 2019-08-05 14:26:32
     **/
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc) {
        LOGGER.debug("修改商品，item={}，desc={}" + item, desc);
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                //响应400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            //保存商品基本数据
            this.itemService.updateItem(item, desc);
            LOGGER.debug("修改商品成功!,itemId={}", item.getId());
            //成功201
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("修改商品失败! title:" + item.getTitle() + "id:" + item.getCid(), e);
            e.printStackTrace();
        }
        //出错500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 功能:<按id单个或批量删除商品>
     *
     * @param ids
     * @return {@link ResponseEntity< Integer>}
     * @author
     * @date 2019-08-05 11:27:01
     **/
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<Integer> deleteItemById(@RequestParam("ids") Object[] ids) {
        try {
            List<Object> list = Arrays.asList(ids);
            //this.itemService.deleteById(ids);
            LOGGER.debug("删除商品的id为：", Arrays.toString(list.toArray()));
            this.itemService.deleteByIds(Item.class, "id", list);
            //成功返回201
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //错误返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<Item> queryById(@PathVariable("itemId") Long itemId) {
        try {
            Item item = this.itemService.queryById(itemId);
            if(null==item){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(item);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}