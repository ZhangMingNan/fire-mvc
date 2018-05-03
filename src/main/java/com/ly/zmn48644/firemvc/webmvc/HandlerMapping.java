package com.ly.zmn48644.firemvc.webmvc;

import java.lang.reflect.Method;

public class HandlerMapping {
    private Object object;
    private Method method;
    private String path;

    public HandlerMapping(Object object, Method method, String path) {
        this.object = object;
        this.method = method;
        this.path = path;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
