package com.taotao.cart.controller;

import com.taotao.cart.pojo.tb_cart;
import com.taotao.cart.pojo.tb_user;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadlocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public String addItemToCart(@PathVariable(value = "itemId")Long itemId){
        tb_user user = UserThreadlocal.get();
        if(null!=user){
            //未登录状态
        }else{
            //登录状态
            this.cartService.addItemToCart(itemId);
        }
        //重定向到购物车列表页面
        return "redirect:/cart/list.html";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView showCartList(){
        ModelAndView mv=new ModelAndView("cart");
        List<tb_cart> cartList=null;
        tb_user user = UserThreadlocal.get();
        if(null==user){
            //未登录
        }else {
            //已登录
            cartList=this.cartService.queryCartList();
        }
        mv.addObject("cartList",cartList);
        return mv;
    }
}