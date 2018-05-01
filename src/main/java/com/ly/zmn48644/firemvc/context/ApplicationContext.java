package com.ly.zmn48644.firemvc.context;

import com.ly.zmn48644.firemvc.beans.BeanDefinition;
import com.ly.zmn48644.firemvc.core.BeanFactory;
import com.ly.zmn48644.firemvc.io.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用上下文
 */
public class ApplicationContext implements BeanFactory {

    private String configLocation;

    private BeanDefinitionReader reader;

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    public ApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        refresh();
    }

    public void refresh() {
        //定位
        this.reader = new BeanDefinitionReader(configLocation);
        //加载
        List<String> beanDefinitions = this.reader.loadBeanDefinitions();

        //注册
        doRegisty(beanDefinitions);

        //依赖注入
        doAutowrited();
    }

    private void doAutowrited() {


    }

    private void doRegisty(List<String> beanDefinitions) {
        for (String className : beanDefinitions) {
            try {
                Class<?> clazz = Resources.classForName(className);

                //排除接口
                if (clazz.isInterface()) {
                    continue;
                }
                BeanDefinition beanDefinition = this.reader.registerBean(className);
                beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);

                //实现了多个接口则会覆盖
                for (Class<?> interfaceName : clazz.getInterfaces()) {
                    beanDefinitionMap.put(interfaceName.getName(), beanDefinition);
                }


            } catch (ClassNotFoundException e) {

            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
