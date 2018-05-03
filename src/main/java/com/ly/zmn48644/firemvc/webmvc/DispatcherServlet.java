package com.ly.zmn48644.firemvc.webmvc;

import com.ly.zmn48644.firemvc.annotation.Controller;
import com.ly.zmn48644.firemvc.annotation.RequestMapping;
import com.ly.zmn48644.firemvc.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final String LOCATION = "contextConfigLocation";

    private Map<String, HandlerMapping> handlerMappingMap = new HashMap<>();
    private Map<HandlerMapping,HandlerAdapter> handlerAdapterMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext context = new ApplicationContext(config.getInitParameter(LOCATION));
        initHandlerMappings(context);
        initHandlerAdapters();
        initViewResolvers(context);
    }

    private void initViewResolvers(ApplicationContext context) {

    }


    private void initHandlerAdapters() {
        for (String path : handlerMappingMap.keySet()) {
            HandlerMapping mapping = handlerMappingMap.get(path);

            Map<String, Integer> paramMap = new HashMap<>();







            handlerAdapterMap.put(mapping,new HandlerAdapter(paramMap));

        }

    }

    private void initHandlerMappings(ApplicationContext context) {
        String[] beanNames = context.getBeanNames();
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Class<?> clazz = bean.getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                String basePath = "";
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    basePath = clazz.getAnnotation(RequestMapping.class).value();
                }
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        String methodPath = method.getAnnotation(RequestMapping.class).value();
                        String path = String.format("%s%s", basePath, methodPath);
                        handlerMappingMap.put(String.format("%s%s", basePath, methodPath), new HandlerMapping(bean, method, path));
                    }
                }
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
