package com.taotao.store.order.controller;

import com.alibaba.fastjson.JSON;
import com.taotao.store.order.bean.TaotaoResult;
import com.taotao.store.order.pojo.Order;
import com.taotao.store.order.pojo.PageResult;
import com.taotao.store.order.pojo.ResultMsg;
import com.taotao.store.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public TaotaoResult createOrder(@RequestBody String json) {
        return orderService.createOrder(json);
    }


    /**
     * 根据订单ID查询订单
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query/{orderId}", method = RequestMethod.GET)
    public String queryOrderById(@PathVariable("orderId") String orderId) {
        Order order = orderService.queryOrderById(orderId);
        String jsonString = JSON.toJSONString(order);
        return jsonString;
    }

    /**
     * 根据用户名分页查询订单
     *
     * @param buyerNick
     * @param page
     * @param count
     * @return
     */
    @ResponseBody
    @RequestMapping("/query/{buyerNick}/{page}/{count}")
    public String queryOrderByUserNameAndPage(@PathVariable("buyerNick") String buyerNick, @PathVariable("page") Integer page, @PathVariable("count") Integer count) {
        PageResult<Order> orderPageResult = orderService.queryOrderByUserNameAndPage(buyerNick, page, count);
        String jsonString = JSON.toJSONString(orderPageResult);
        System.out.println(jsonString);
        return jsonString;
    }


    /**
     * 修改订单状态
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changeOrderStatus", method = RequestMethod.POST)
    public ResultMsg changeOrderStatus(@RequestBody String json) {
        return orderService.changeOrderStatus(json);
    }
}