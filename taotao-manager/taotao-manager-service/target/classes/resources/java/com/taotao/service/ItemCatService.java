package com.taotao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.service.redis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-07-30 22:20
 */
@Service
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String REDIS_KEY="TAOTAO_MANAGER_ITEM_ALL";

    /**
     * 全部查询，并且生成树状结构
     *
     * @return
     */
    public ItemCatResult queryAllToTree() {
        ItemCatResult result = new ItemCatResult();
        //先从缓存中命中，如果命中则直接返回，没有命中程序继续执行，不影响下面的业务
        try {
            String cachaData = this.jedisClient.get(REDIS_KEY);
            if (StringUtils.isNotEmpty(cachaData)) {
                return OBJECT_MAPPER.readValue(cachaData, ItemCatResult.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();
        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }
        try {
            this.jedisClient.setAndExpire(REDIS_KEY,result,50000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}