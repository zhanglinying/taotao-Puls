package com.taotao.service;

import com.taotao.manager.utils.UUIDUtils;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-01 15:29
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    public void saveItem(Item item, String desc) {
        //初始化数据
        item.setStatus(1);
        //强制设置id为null
        long uuid= (long) UUIDUtils.getUUIDInOrderId();
        System.out.println(uuid);
        item.setId(uuid);
        super.save(item);
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(uuid);
        itemDesc.setItemDesc(desc);
        //保存描述数据
        this.itemDescService.saveService(itemDesc);
    }
}