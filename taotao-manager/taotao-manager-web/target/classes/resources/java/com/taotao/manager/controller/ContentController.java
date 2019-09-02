package com.taotao.manager.controller;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.pojo.Content;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 小浪浪
 * 内容管理
 * @version 1.0
 * @date 2019-08-09 16:57
 */
@Controller
@RequestMapping(value ="content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryContentList(
            @RequestParam(value = "categoryId")Long categoryId,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows){
            try {
                EasyUIResult easyUIResult=this.contentService.queryContentList(categoryId,page,rows);
                return ResponseEntity.ok(easyUIResult);
            }catch (Exception e){
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }



    /**
     * 功能:<新增内容>
     * @author
     * @date 2019-08-10 11:16:53
     * @param content
     * @return {@link ResponseEntity< Void>}
     **/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content){
        try {
            content.setId(null);
            this.contentService.saveService(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}