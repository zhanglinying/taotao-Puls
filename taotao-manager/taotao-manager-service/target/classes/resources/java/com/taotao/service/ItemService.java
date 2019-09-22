package com.taotao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.utils.UUIDUtils;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ApiService apiService;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("http://www.taotao.com")
    private String TAOTAO_WEB_URL;

    public void saveItem(Item item, String desc) {
        //初始化数据
        item.setStatus(1);
        //生产id
        long uuid = (long) UUIDUtils.getUUIDInOrderId();
        System.out.println(uuid);
        item.setId(uuid);
        super.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(uuid);
        itemDesc.setItemDesc(desc);
        //保存描述数据
        this.itemDescService.saveService(itemDesc);
    }

    public PageInfo<Item> queryPageList(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");

        //设置分页参数
        PageHelper.startPage(page, rows);
        List<Item> items = this.itemMapper.selectByExample(example);
        return new PageInfo<Item>(items);
    }

    public void updateItem(Item item, String desc) {
        //强制设置不能修改的字段设置为null
        item.setStatus(null);
        item.setCreated(null);
        super.updateService(item);
        //修改描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateService(itemDesc);
        //发送消息
        sendMsg(item.getId(),"update");
    }

    private void sendMsg(Long itemId,String type){
        try {
            //发送mq消息给其他系统
            Map<String, Object> result = new HashMap<>();
            result.put("ItemId",itemId);
            result.put("type", type);
            result.put("date", System.currentTimeMillis());
            this.rabbitTemplate.convertAndSend("item."+type, OBJECT_MAPPER.writeValueAsString(result));
            System.out.println("消息已通知!");
        } catch (Exception e) {
            System.out.println("消息通知失败!");
            e.printStackTrace();
        }
    }
}