package com.ly.zmn48644.firemvc.beans;

public class BeanWrapper {
    //代理对象
    private Object wrapperInstance;
    //原始对象
    private Object originalInstance;



    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public void setWrapperInstance(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }

    public Object getOriginalInstance() {
        return originalInstance;
    }

    public void setOriginalInstance(Object originalInstance) {
        this.originalInstance = originalInstance;
    }
}
