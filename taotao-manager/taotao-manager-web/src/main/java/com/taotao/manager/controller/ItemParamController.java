package com.taotao.manager.controller;

import com.taotao.pojo.ItemParam;
import com.taotao.service.ItemParamService;
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
 * @date 2019-08-05 17:40
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 功能:<查询所有商品规格>
     *
     * @return {@link ResponseEntity< List< ItemParam>>}
     * @author
     * @date 2019-08-06 15:40:07
     **/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<ItemParam>> queryItemParamList() {
        try {
            List<ItemParam> itemParamList = this.itemParamService.queryAll();
            return ResponseEntity.ok(itemParamList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 功能:<根据商品类目查询规格参数>
     *
     * @param itemCarId
     * @return {@link ResponseEntity< ItemParam>}
     * @author
     * @date 2019-08-06 14:21:31
     **/
    @RequestMapping(value = "{itemCarId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParamCatId(@PathVariable("itemCarId") Long itemCarId) {
        try {
            ItemParam record = new ItemParam();
            record.setItemCatId(itemCarId);
            ItemParam itemParam = this.itemParamService.queryOne(record);
            if (null == itemParam) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 功能:<新增商品规格参数模板>
     *
     * @param itemParam
     * @param itemCarId
     * @return {@link ResponseEntity< Void>}
     * @author
     * @date 2019-08-06 15:29:37
     **/
    @RequestMapping(value = "{itemCarId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParma(ItemParam itemParam, @PathVariable("itemCarId") Long itemCarId) {
        try {
            itemParam.setItemCatId(itemCarId);
            this.itemParamService.saveService(itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 功能:<按id单个或批量删除规格参数>
     *
     * @param ids
     * @return {@link ResponseEntity< Integer>}
     * @author
     * @date 2019-08-06 17:01:06
     **/
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<Integer> deleteItemParamByIds(@RequestParam("ids") Object[] ids) {
        try {
            LOGGER.debug("删除规格id为:", Arrays.toString(ids));
            List<Object> list = Arrays.asList(ids);
            this.itemParamService.deleteByIds(ItemParam.class, "id", list);
            LOGGER.debug("删除规格成功!");
            //成功返回201

            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //错误返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}