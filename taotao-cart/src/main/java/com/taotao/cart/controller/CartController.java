package com.taotao.cart.controller;

import com.alibaba.fastjson.JSON;
import com.taotao.cart.pojo.tb_cart;
import com.taotao.cart.pojo.tb_user;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadlocal;
import javafx.scene.chart.ValueAxis;
import org.apache.commons.lang.text.StrTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.NumberUp;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable(value = "itemId") Long itemId) {
        tb_user user = UserThreadlocal.get();
        if (null == user) {
            //未登录状态
        } else {
            //登录状态
            this.cartService.addItemToCart(itemId);
        }
        //重定向到购物车列表页面
        return "redirect:/cart/list.html";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showCartList() {
        ModelAndView mv = new ModelAndView("cart");
        List<tb_cart> cartList = null;
        tb_user user = UserThreadlocal.get();
        if (null == user) {
            //未登录
        } else {
            //已登录
            cartList = this.cartService.queryCartList();
        }
        mv.addObject("cartList", cartList);
        return mv;
    }

    /**
     * 修改购买数量
     *
     * @param itemId
     * @param num    最终购买的数量
     * @return
     */
    @RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable(value = "itemId") Long itemId,
                                          @PathVariable(value = "num") Integer num) {
        tb_user user = UserThreadlocal.get();
        if (null == user) {
            //未登录
        } else {
            //已登录
            this.cartService.updateNum(itemId, num);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
    public String deleteCartById(@PathVariable(value = "itemId") Long itemId) {
        tb_user user = UserThreadlocal.get();
        if (null == user) {
            //未登录
        } else {
            //已登录
            this.cartService.deleteCartById(itemId);
        }
        return "redirect:/cart/list.html";
    }

    /**
     * 对外提供接口，根据用户id查询购物车信息
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "userId")
    @ResponseBody
    public String queryCartListByUserById(@RequestParam(value = "userId") Long userId) {
        try {
            List<tb_cart> cartList = this.cartService.queryCartList(userId);
            if (null == cartList || cartList.isEmpty()) {
                return null;
            }
            System.out.println(cartList);
            return JSON.toJSONString(cartList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}