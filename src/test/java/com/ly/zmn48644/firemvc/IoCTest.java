package com.ly.zmn48644.firemvc;

import com.ly.zmn48644.demo.controller.IndexController;
import com.ly.zmn48644.firemvc.context.ApplicationContext;

public class IoCTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("application.properties");

        IndexController indexController = (IndexController) applicationContext.getBean("indexController");

        indexController.home();
    }
}