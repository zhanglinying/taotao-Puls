package com.taotao.service;

import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-07-30 22:20
 */
@Service
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private ItemCatMapper itemCatMapper;
}