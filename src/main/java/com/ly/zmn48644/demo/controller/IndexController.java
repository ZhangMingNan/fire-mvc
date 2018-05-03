package com.ly.zmn48644.demo.controller;

import com.ly.zmn48644.demo.service.IndexService;
import com.ly.zmn48644.firemvc.annotation.Autowrited;
import com.ly.zmn48644.firemvc.annotation.Controller;
import com.ly.zmn48644.firemvc.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowrited()
    private IndexService indexService;

    @RequestMapping("home")
    public void home() {
        Date date = indexService.localTime();
        System.out.println("当前时间:" + date);
    }

    public IndexService getIndexService() {
        return indexService;
    }

    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }
}
