package com.taotao.manager.utils;

import java.util.UUID;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-01 16:45
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static Integer getUUIDInOrderId(){
        Integer orderId=UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        return orderId;
    }


}