package com.taotao.web.controller;

import com.taotao.web.bean.Item;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.tb_cart;
import com.taotao.web.bean.tb_user;
import com.taotao.web.handlerInterceptor.UserLoginHandlerInterceptor;
import com.taotao.web.service.CartService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadlocal;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /**
     * 订单信息
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId) {
        ModelAndView modelAndView = new ModelAndView("order");
        Item item = this.itemService.queryItemByItemId(itemId);
        modelAndView.addObject("item", item);
        return modelAndView;
    }

    /**
     * 创建订单
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView toCartOrder() {
        ModelAndView mv = new ModelAndView("order-cart");
        List<tb_cart> cartList = null;
        tb_user user = UserThreadlocal.get();
        cartList=this.cartService.queryCartListUserById(user.getId());
        System.out.println(Arrays.asList(cartList));
        mv.addObject("carts", cartList);
        return mv;
    }

    /**
     * 提交订单
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitOrder(Order order) {
        Map<String, Object> result = new HashMap<String, Object>();
        String orderId = null;
        try {
            orderId = this.orderService.submitOrder(order);
            if (StringUtils.isEmpty(orderId)) {
                //提交订单失败
                result.put("status", 300);
            } else {
                //提交订单成功
                result.put("status", 200);
                result.put("data", orderId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView success(@RequestParam(value = "id") String orderId) {
        ModelAndView modelAndView = new ModelAndView("success");
        try {
            Order order = this.orderService.queryOrderById(orderId);
            //返回用户订单信息
            modelAndView.addObject("order", order);
            System.out.println(order.getOrderId() + order.getPayment());
            //当前时间后推两天
            modelAndView.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}