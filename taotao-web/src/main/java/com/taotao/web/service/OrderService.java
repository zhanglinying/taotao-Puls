package com.taotao.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.sound.FFT;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.tb_user;
import com.taotao.web.httpclient.HttpResult;
import com.taotao.web.threadlocal.UserThreadlocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;

@Service
public class OrderService {

    @Autowired
    private ApiService apiService;

    private static final String TAOTAO_ORDER_URL = "http://order.taotao.com";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String submitOrder(Order order) throws IOException {
        tb_user user = UserThreadlocal.get();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        try {
            String url = TAOTAO_ORDER_URL + "/order/create";
            HttpResult httpResult = this.apiService.doPostJSON(url, OBJECT_MAPPER.writeValueAsString(order));
            if (httpResult.getCode().intValue() == 200) {
                String data = httpResult.getData();
                JsonNode jsonNode = OBJECT_MAPPER.readTree(data);
                if (jsonNode.get("status").intValue() == 200) {
                    return jsonNode.get("data").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order queryOrderById(String orderId) throws IOException {
        String url=TAOTAO_ORDER_URL+"/order/query/"+orderId;
        String jsonData = this.apiService.doGet(url);
        if(!StringUtils.isEmpty(jsonData)){
            System.out.println("success");
            return OBJECT_MAPPER.readValue(jsonData,Order.class);
        }
        return null;
    }
}