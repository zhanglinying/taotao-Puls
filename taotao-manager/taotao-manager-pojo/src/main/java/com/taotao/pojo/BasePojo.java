package com.taotao.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BasePojo {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    

}
