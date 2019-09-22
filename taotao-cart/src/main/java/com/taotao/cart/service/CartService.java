package com.taotao.cart.service;

import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Item;
import com.taotao.cart.pojo.tb_cart;
import com.taotao.cart.pojo.tb_user;
import com.taotao.cart.threadlocal.UserThreadlocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId) {
        tb_user user = UserThreadlocal.get();
        tb_cart record = new tb_cart();
        record.setItemId(itemId);
        record.setUserId(user.getId());
        tb_cart cart = this.cartMapper.selectOne(record);
        if (null == cart) {
            //购物车不存在改商品
            Item item = this.itemService.queryItemByItemId(itemId);
            if (null == item) {
                //Todo 给用户提示
                return;
            }
            cart = new tb_cart();
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            cart.setItemId(itemId);
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());
            cart.setNum(1);
            cart.setUserId(user.getId());
            this.cartMapper.insert(cart);
        } else {
            //改商品存在购物车当中,数量相加,Todo
            cart.setNum(cart.getNum() + 1);
            cart.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    public List<tb_cart> queryCartList() {
        tb_user user = UserThreadlocal.get();
        Example example = new Example(tb_cart.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        example.setOrderByClause("created DESC"); //根据创建时间倒序排序
        return this.cartMapper.selectByExample(example);
    }
}