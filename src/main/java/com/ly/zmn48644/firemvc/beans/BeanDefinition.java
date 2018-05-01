package com.ly.zmn48644.firemvc.beans;

public class BeanDefinition {
    private String beanClassName;
    private String factoryBeanName;

    public BeanDefinition(String factoryBeanName, String beanClassName) {
        this.beanClassName = beanClassName;
        this.factoryBeanName = factoryBeanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
