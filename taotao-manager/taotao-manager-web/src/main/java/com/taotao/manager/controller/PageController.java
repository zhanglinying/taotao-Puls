package com.taotao.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-07-30 14:38
 */

@Controller
@RequestMapping(value = "page")
public class PageController {
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/item-list")
    public String list() {
        return "item-list";
    }

    @RequestMapping(value = "/item-param-list")
    public String itemList() {
        return "item-param-list";
    }

    @RequestMapping(value = "/item-add")
    public String add() {
        return "item-add";
    }

    @RequestMapping(value = "/item-edit")
    public String edit(){return  "item-edit";}

}
