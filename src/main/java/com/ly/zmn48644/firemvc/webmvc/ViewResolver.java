package com.ly.zmn48644.firemvc.webmvc;

import java.io.File;

public class ViewResolver {

    private String view;

    private File template;

    public String resolver(ModelAndView modelAndView){

        return null;
    }

    public ViewResolver(String view, File template) {
        this.view = view;
        this.template = template;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }
}
