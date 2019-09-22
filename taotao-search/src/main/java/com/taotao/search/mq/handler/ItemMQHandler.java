package com.taotao.search.mq.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Queue;

public class ItemMQHandler {

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    @Autowired
    private ItemService itemService;
    @Autowired
    private HttpSolrServer httpSolrServer;

    public void Foo(String msg){
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(msg);
            String itemId = jsonNode.get("ItemId").asText();
            String type = jsonNode.get("type").asText();
            if(StringUtils.equals(type,"insert")||StringUtils.equals(type,"update")){
                //从后台查询数据
                Item item = this.itemService.queryItem(itemId);
                if(item!=null){
                    try {
                        this.httpSolrServer.addBean(item);
                        this.httpSolrServer.commit();
                    } catch (SolrServerException e) {
                        e.printStackTrace();
                    }
                }
            }else if (StringUtils.equals(type,"delete")){
                //删除索引数据
                try {
                    this.httpSolrServer.deleteById(itemId);
                    this.httpSolrServer.commit();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}