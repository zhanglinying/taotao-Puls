package com.taotao.manager.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-08 9:34
 */
@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    /**
     * 功能:<对外提供接口查询商品类目数据>
     * @author
     * @date 2019-08-08 09:45:44
     * @return {@link ResponseEntity< ItemCatResult>}
     **/
  /*  @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCat(){
        try{
            ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
            return ResponseEntity.ok(itemCatResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }*/


  /*  *
     * 功能:<对外提供接口查询商品类目数据>
     * @author
     * @date 2019-08-08 09:45:44
     * @return {@link ResponseEntity< ItemCatResult>}
     **/
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<String> queryItemCat(@RequestParam("callback")String callback){
        try{
            ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
            String json = OBJECT_MAPPER.writeValueAsString(itemCatResult);
            if(StringUtils.isEmpty(callback)){
                return ResponseEntity.ok(json);
            }
            return ResponseEntity.ok(callback+"("+json+")");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}