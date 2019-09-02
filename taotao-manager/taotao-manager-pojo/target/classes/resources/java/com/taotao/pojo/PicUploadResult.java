package com.taotao.pojo;

import java.io.Serializable;

public class PicUploadResult implements Serializable {

    //判断是否上传成功 0成功 1失败
    private Integer error;
    
    private String url;
    
    private String width;
    
    private String height;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    
    

}
