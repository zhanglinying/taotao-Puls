package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.utils.UUIDUtils;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-01 15:29
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemMapper itemMapper;

    public void saveItem(Item item, String desc) {
        //初始化数据
        item.setStatus(1);
        //生产id
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

    public PageInfo<Item> queryPageList(Integer page, Integer rows) {
        Example example=new Example(Item.class);
        example.setOrderByClause("updated DESC");

        //设置分页参数
        PageHelper.startPage(page,rows);
        List<Item> items = this.itemMapper.selectByExample(example);
        return new PageInfo<Item>(items);
    }
}