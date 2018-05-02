package com.ly.zmn48644.firemvc.context;

import com.ly.zmn48644.firemvc.annotation.Autowrited;
import com.ly.zmn48644.firemvc.annotation.Controller;
import com.ly.zmn48644.firemvc.annotation.Service;
import com.ly.zmn48644.firemvc.beans.BeanDefinition;
import com.ly.zmn48644.firemvc.beans.BeanWrapper;
import com.ly.zmn48644.firemvc.core.BeanFactory;
import com.ly.zmn48644.firemvc.io.Resources;

import java.lang.reflect.Field;
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

    private Map<String, BeanWrapper> beanWrapperMap = new HashMap<>();

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
        for (String beanName : beanDefinitionMap.keySet()) {
            getBean(beanName);
        }

        for (String beanName : beanWrapperMap.keySet()) {
            BeanWrapper beanWrapper = beanWrapperMap.get(beanName);
            populateBean(beanWrapper);
        }

    }

    private void populateBean(BeanWrapper beanWrapper) {
        Object instance = beanWrapper.getOriginalInstance();
        Class<?> clazz = instance.getClass();

        if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowrited.class)) {
                    Autowrited fieldAnnotation = field.getAnnotation(Autowrited.class);
                    String beanName = fieldAnnotation.value();
                    try {
                        //通过反射设置属性值
                        if ("".equals(beanName) || beanName == null) {
                            beanName = field.getType().getName();
                        }
                        field.setAccessible(true);
                        System.out.println(beanName);
                        if ( beanWrapperMap.get(beanName)!=null){
                            field.set(instance, beanWrapperMap.get(beanName).getOriginalInstance());
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition != null) {
            try {
                Class<?> clazz = Resources.classForName(beanDefinition.getBeanClassName());
                BeanWrapper beanWrapper = new BeanWrapper();
                Object instance = clazz.newInstance();
                beanWrapper.setOriginalInstance(instance);
                beanWrapper.setWrapperInstance(instance);
                beanWrapperMap.put(beanDefinition.getFactoryBeanName(), beanWrapper);
                return beanWrapper.getOriginalInstance();
            } catch (Exception e) {

            }
        }
        return null;
    }
}
