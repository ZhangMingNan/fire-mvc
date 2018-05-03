package com.ly.zmn48644.firemvc.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HandlerAdapter {

    private Map<String,Integer> paramMap;

    public HandlerAdapter(Map<String, Integer> paramMap) {
        this.paramMap = paramMap;
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, HandlerMapping mapping) {

        return null;
    }
}
