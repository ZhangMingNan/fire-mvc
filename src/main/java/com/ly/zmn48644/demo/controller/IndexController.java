package com.ly.zmn48644.demo.controller;

import com.ly.zmn48644.demo.service.IndexService;
import com.ly.zmn48644.firemvc.annotation.Autowrited;
import com.ly.zmn48644.firemvc.annotation.Controller;
import com.ly.zmn48644.firemvc.annotation.RequestMapping;
import com.ly.zmn48644.firemvc.annotation.RequestParam;
import com.ly.zmn48644.firemvc.webmvc.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowrited()
    private IndexService indexService;

    @RequestMapping("/home")
    public ModelAndView home(@RequestParam("city") String city, @RequestParam("country") String country) {
        Date date = indexService.localTime();
        System.out.println(city + ":当前时间:" + date);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("home");
        modelAndView.addAttribute("city",city);
        modelAndView.addAttribute("country",country);
        return modelAndView;
    }

    public IndexService getIndexService() {
        return indexService;
    }

    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }
}
