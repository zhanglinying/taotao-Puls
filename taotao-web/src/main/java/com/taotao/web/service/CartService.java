package com.taotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.web.bean.tb_cart;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ApiService apiService;

    @Value("http://cart.taotao.com")
    private String TAOTAO_CART_URL;

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    public List<tb_cart> queryCartListUserById(Long id) {
        //查询购物车系统提供的查询购物车接口
        String url=TAOTAO_CART_URL+"/service/cart?userId="+id;
        System.out.println(url);
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            System.out.println(jsonData+"------------------------");
           return OBJECT_MAPPER.readValue(jsonData,OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class,tb_cart.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}