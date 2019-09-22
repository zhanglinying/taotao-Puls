package com.taotao.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.tb_user;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private ApiService apiService;

    @Value("http://sso.taotao.com")
    public String TAOTAO_SSO_URL;

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    public tb_user queryUserByToken(String token) throws IOException {
        String url=TAOTAO_SSO_URL+"/service/user/"+token;
        String jsonData=this.apiService.doGet(url);
        String jsonData2=jsonData.replaceAll("\\\\","");
        String jsonData3=jsonData2.substring(1,jsonData2.length()-1);
        if(!StringUtils.isEmpty(jsonData)){
            return OBJECT_MAPPER.readValue(jsonData3,tb_user.class);
        }
        return null;
    }
}