package com.taotao.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 小浪浪
 * @version 1.0
 * @date 2019-08-07 17:25
 */
@Controller
@RequestMapping("/page")
public class PageController {

        @RequestMapping(value = "/index")
        public String index(){ return "index";}

        @RequestMapping(value = "/item")
        public String item(){return "item";}
}