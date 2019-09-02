package com.taotao.mapper;

import com.taotao.pojo.Content;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-09 16:10
 */
public interface ContentMapper extends Mapper<Content> {
    List<Content> queryContentList(Long categoryId);
}