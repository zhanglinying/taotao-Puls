package com.taotao.pojo;


import com.fasterxml.jackson.annotation.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_item_desc")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDesc extends BasePojo{

    @Id//对应tb_item中的id
    private Long itemId;

    private String itemDesc;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }


}
