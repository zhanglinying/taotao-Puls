package com.taotao.cart.threadlocal;


import com.taotao.cart.pojo.tb_user;

public class UserThreadlocal {

    private static final ThreadLocal<tb_user> LOCAL=new ThreadLocal<tb_user>();


    public static void set(tb_user user){
        LOCAL.set(user);
    }

    public static tb_user get(){
        return LOCAL.get();
    }

}