package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    private ApiService apiService;

    @Value("http://manager.taotao.com")
    private String TAOTAO_MANAGER_URL;

    @Value("/rest/content?categoryId=89&page=1&rows=6")
    private String INDEX_AD1_URL;

    @Value("/rest/content?categoryId=90&page=1&rows=6")
    private String INDEX_AD2_URL;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 返回首页大广告内容
     *
     * @return
     */
    public String queryIndexAd1() {
        String url = TAOTAO_MANAGER_URL+INDEX_AD1_URL;
        System.out.println(url);
        try {
            String jsonData = this.apiService.doGet(url);
            if (!StringUtils.isEmpty(jsonData)) {
                //解析json，生产前端所需要的json
                JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonData);
                ArrayNode rows = (ArrayNode) jsonNode.get("rows");
                List<Map<String, Object>> result = new ArrayList<>();
                for (JsonNode arrayNode : rows) {
                    Map<String, Object> linkedHashMap = new LinkedHashMap<>();
                    linkedHashMap.put("srcB", arrayNode.get("pic").asText());
                    linkedHashMap.put("height", 200);
                    linkedHashMap.put("alt", arrayNode.get("title").asText());
                    linkedHashMap.put("width", 670);
                    linkedHashMap.put("src", arrayNode.get("pic").asText());
                    linkedHashMap.put("widthB", 550);
                    linkedHashMap.put("href", arrayNode.get("url").asText());
                    linkedHashMap.put("heightB", 240);
                    result.add(linkedHashMap);
                }
                return OBJECT_MAPPER.writeValueAsString(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回右侧小广告数据
     * @return
     */
    public String queryIndexAd2() {
        String url = TAOTAO_MANAGER_URL+INDEX_AD2_URL;
        System.out.println(url);
        try {
            String jsonData = this.apiService.doGet(url);
            if (!StringUtils.isEmpty(jsonData)) {
                //解析json，生产前端所需要的json
                JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonData);
                ArrayNode rows = (ArrayNode) jsonNode.get("rows");
                List<Map<String, Object>> result = new ArrayList<>();
                for (JsonNode arrayNode : rows) {
                    Map<String, Object> linkedHashMap = new LinkedHashMap<>();
                    linkedHashMap.put("width",310);
                    linkedHashMap.put("height", 70);
                    linkedHashMap.put("alt", arrayNode.get("title").asText());
                    linkedHashMap.put("width", 670);
                    linkedHashMap.put("src", arrayNode.get("pic").asText());
                    linkedHashMap.put("widthB", 210);
                    linkedHashMap.put("href", arrayNode.get("url").asText());
                    linkedHashMap.put("heightB", 70);
                    result.add(linkedHashMap);
                }
                return OBJECT_MAPPER.writeValueAsString(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}