package com.taotao.web.bean;/**
 * @author : 小浪浪
 * @date : 15:01 2019/8/20
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 *@author 小浪浪
 *@date 2019/8/20 15:01
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends com.taotao.pojo.Item {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updated;
    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public String[] getImages(){
        return StringUtils.split(super.getImage(),",");
    }
}