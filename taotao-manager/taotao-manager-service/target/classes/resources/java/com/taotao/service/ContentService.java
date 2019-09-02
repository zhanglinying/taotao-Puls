package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.mapper.ContentMapper;
import com.taotao.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-09 16:12
 */
@Service
public class ContentService extends BaseService<Content> {

    @Autowired
    private ContentMapper contentMapper;


    public EasyUIResult queryContentList(Long categoryId, Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        List<Content> contentList = this.contentMapper.queryContentList(categoryId);
        PageInfo<Content> pageInfo=new PageInfo<Content>(contentList);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}