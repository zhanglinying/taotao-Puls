package com.taotao.search.controller;

import com.taotao.search.bean.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * @param keyWord
     * @param page
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "q") String keyWord,
                               @RequestParam(value = "page",defaultValue = "1") Integer page) {
        ModelAndView modelAndView = new ModelAndView("search");
        try {
            System.out.println(keyWord);
            //搜索商品
            SearchResult searchResult = this.searchService.search(keyWord, page);
            modelAndView.addObject("query", keyWord);//搜索关键字
            modelAndView.addObject("itemList", searchResult.getData());//商品列表
            modelAndView.addObject("page", page);//页数
            int total=searchResult.getTotal().intValue();
            System.out.println(total);
            int pages=total%SearchService.ROWS==0?total/SearchService.ROWS:total/SearchService.ROWS+1;
            modelAndView.addObject("pages", pages);//总页数
            System.out.println(pages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}