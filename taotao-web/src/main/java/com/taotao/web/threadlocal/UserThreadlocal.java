package com.taotao.web.threadlocal;

import com.taotao.web.bean.tb_user;

public class UserThreadlocal {

    private static final ThreadLocal<tb_user> LOCAL=new ThreadLocal<tb_user>();


    public static void set(tb_user user){
        LOCAL.set(user);
    }

    public static tb_user get(){
        return LOCAL.get();
    }

}