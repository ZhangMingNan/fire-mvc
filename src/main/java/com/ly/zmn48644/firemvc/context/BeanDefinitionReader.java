package com.ly.zmn48644.firemvc.context;


import com.ly.zmn48644.firemvc.beans.BeanDefinition;
import com.ly.zmn48644.firemvc.io.Resources;
import com.ly.zmn48644.firemvc.io.VFS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionReader {

    private Properties config = new Properties();

    private static final String SCAN_PACKAGE = "scanPackage";

    private List<String> registyBeanClasses = new ArrayList<>();

    public BeanDefinitionReader(String configLocation) {
        try {
            config = Resources.getResourceAsProperties(configLocation.substring(configLocation.indexOf(":")+1));
        } catch (IOException e) {

        }
        doScanner(config.getProperty(SCAN_PACKAGE));

    }

    /**
     * 解析指定包下面的所有类
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        try {
            //递归加载指定路径下的所有文件
            List<String> children = VFS.getInstance().list(scanPackage.replaceAll("\\.", "/"));
            for (String child : children) {
                if (child.endsWith(".class")) {
                    registyBeanClasses.add(child.replace(".class", "").replaceAll("/", "."));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> loadBeanDefinitions() {
        return registyBeanClasses;
    }

    public BeanDefinition registerBean(String className) {
        if (registyBeanClasses.contains(className)) {
            String factoryBeanName = lowerFirstCase(className.substring(className.lastIndexOf(".") + 1));
            BeanDefinition beanDefinition = new BeanDefinition(factoryBeanName, className);
            return beanDefinition;
        }
        return null;
    }

    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
















