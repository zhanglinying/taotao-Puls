package com.taotao.manager.controller;

import com.taotao.pojo.ContentCategory;
import com.taotao.service.ContentCategoryService;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小浪浪
 * 网站分类管理
 * @version 1.0
 * @date 2019-08-09 16:58
 */
@Controller
@RequestMapping(value = "content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService categoryService;

    /**
     * 功能:<根据父节点id查询子节点>
     *
     * @param categoryId
     * @return {@link ResponseEntity< List< ContentCategory>>}
     * @author
     * @date 2019-08-09 17:09:41
     **/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListByCategoryId(
            @RequestParam(value = "id", defaultValue = "0") Long categoryId) {
        try {
            ContentCategory record = new ContentCategory();
            record.setParentId(categoryId);
            List<ContentCategory> categoryList = this.categoryService.queryListByWhere(record);
            if (null == categoryList || categoryList.isEmpty()) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 功能:<新增子节点>
     *
     * @param category
     * @return {@link ResponseEntity< ContentCategory>}
     * @author
     * @date 2019-08-09 18:11:09
     **/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory category) {
        try {
            category.setId(null);
            category.setIsParent(false);
            category.setSortOrder(1);
            category.setStatus(1);
            this.categoryService.save(category);
            ContentCategory parent = this.categoryService.queryById(category.getParentId());
            //判断父节点的isParent是否为true，如果不是则修改为true
            if(!parent.getIsParent()){
                parent.setIsParent(true);
                this.categoryService.updateService(parent);
            }
            //调用service执行，确保事务
            //ContentCategory contentCategory = this.categoryService.saveContentCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 功能:<重命名>
     *
     * @param category
     * @return {@link ResponseEntity< Void>}
     * @author
     * @date 2019-08-09 18:21:56
     **/
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> renameContentCategory(ContentCategory category) {
        this.categoryService.updateService(category);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 功能:<删除节点以及所有的子节点>
     * @author
     * @date 2019-08-09 18:55:50
     * @param category
     * @return {@link ResponseEntity< Void>}
     **/
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCategory(ContentCategory category) {
        try {
            //查找所有的子节点
            List<Object> nodes = new ArrayList<>();
            nodes.add(category.getId());
            this.findAllSubNode(category.getId(),nodes);
            //删除所有的子节点
            this.categoryService.deleteByIds(ContentCategory.class,"id",nodes);
            //判断当前节点的父节点是否还有其他的子节点，如果没有，修改isParent为false
            ContentCategory record=new ContentCategory();
            record.setParentId(category.getParentId());
            List<ContentCategory> list = this.categoryService.queryListByWhere(record);
            if(null == list || list.isEmpty()){
                ContentCategory parent=new ContentCategory();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                this.categoryService.updateService(parent);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private void findAllSubNode(Long parentId, List<Object> nodes) {
        ContentCategory record=new ContentCategory();
        record.setParentId(parentId);
        List<ContentCategory> categoryList = this.categoryService.queryListByWhere(record);
        for (ContentCategory category: categoryList){
            nodes.add(category.getId());
            //判断该节点是否为父节点，如果是，进行递归
            if(category.getIsParent()){
                this.findAllSubNode(category.getId(),nodes);
            }
        }
    }
}