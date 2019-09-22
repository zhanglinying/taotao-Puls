package com.taotao.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemService {

    @Autowired
    private ApiService apiService;

    @Value("http://manager.taotao.com")
    private String TAOTAO_MANAGER_URL;

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    public Item queryItem(String itemId){
        String url=TAOTAO_MANAGER_URL+"/rest/item/"+itemId;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            return OBJECT_MAPPER.readValue(jsonData, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}