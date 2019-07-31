package com.taotao.service;

import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-07-30 22:20
 */
@Service
public class ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 功能:<根据父节点Id查询商品类目列表>
     * @author
     * @date 2019-07-30 22:56:20
     * @param parentId
     * @return {@link List< ItemCat>}
     **/
    public List<ItemCat> queryItemCatListByParentId(Long parentId) {
        ItemCat itemCat=new ItemCat();
        itemCat.setParentId(parentId);
        return this.itemCatMapper.select(itemCat);
    }
}