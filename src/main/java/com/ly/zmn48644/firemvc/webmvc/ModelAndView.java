package com.ly.zmn48644.firemvc.webmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {

    private String view;

    private Map<String, Object> model = new HashMap<>();

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public void addAttribute(String key, Object value) {
        model.put(key, value);
    }
}
