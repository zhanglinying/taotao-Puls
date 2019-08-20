package com.taotao.service;

import com.taotao.pojo.ContentCategory;
import org.springframework.stereotype.Service;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-09 16:13
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    public ContentCategory saveContentCategory(ContentCategory category) {
        category.setId(null);
        category.setIsParent(false);
        category.setSortOrder(1);
        category.setStatus(1);
        super.saveService(category);
        ContentCategory parent = super.queryById(category.getParentId());
        //判断父节点的isParent是否为true，如果不是则修改为true
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            super.updateService(parent);
        }
        return parent;
    }
}