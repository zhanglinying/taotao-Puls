package com.taotao.web.controller;

import com.taotao.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        //大广告位数据
        String indexAd1 = this.indexService.queryIndexAd1();
        modelAndView.addObject("indexAd1", indexAd1);
        //右侧小广告数据
        String indexAd2 = this.indexService.queryIndexAd2();
        modelAndView.addObject("indexAd2", indexAd2);
        return modelAndView;
    }
}