package com.ly.zmn48644.firemvc.webmvc;

import com.ly.zmn48644.firemvc.annotation.Controller;
import com.ly.zmn48644.firemvc.annotation.RequestMapping;
import com.ly.zmn48644.firemvc.annotation.RequestParam;
import com.ly.zmn48644.firemvc.context.ApplicationContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final String LOCATION = "contextConfigLocation";

    private static final String TEMPLATE_PREFIX = "templatePrefix";
    private static final String TEMPLATE_SUFFIX = "templateSuffix";


    private Map<String, HandlerMapping> handlerMappingMap = new HashMap<>();

    private Map<HandlerMapping, HandlerAdapter> handlerAdapterMap = new HashMap<>();

    //初始化视图解析器
    private Map<String, ViewResolver> viewResolverMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext context = new ApplicationContext(config.getInitParameter(LOCATION));
        initHandlerMappings(context);
        initHandlerAdapters();
        initViewResolvers(context, config);
    }

    private void initViewResolvers(ApplicationContext context, ServletConfig config) {
        //读取模板文件的根路径

        String prefix = context.getConfig().getProperty(TEMPLATE_PREFIX);
        String suffix = context.getConfig().getProperty(TEMPLATE_SUFFIX);

        File resourceAsFile = new File(config.getServletContext().getRealPath(prefix));
        for (File templateFile : resourceAsFile.listFiles()) {
            String view = StringUtils.substringBetween(templateFile.getAbsolutePath(), prefix, suffix);
            System.out.println(view);
            viewResolverMap.put(view, new ViewResolver(view, templateFile));
        }


    }


    private void initHandlerAdapters() {
        for (String path : handlerMappingMap.keySet()) {
            HandlerMapping mapping = handlerMappingMap.get(path);
            Map<String, Integer> paramMap = new HashMap<>();
            Annotation[][] parameterAnnotations = mapping.getMethod().getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] annotations = parameterAnnotations[i];
                for (Annotation annotation : annotations) {
                    if (annotation instanceof RequestParam) {
                        String paramName = ((RequestParam) annotation).value();
                        paramMap.put(paramName, i);
                    }
                }
            }
            handlerAdapterMap.put(mapping, new HandlerAdapter(paramMap));
        }
    }

    private void initHandlerMappings(ApplicationContext context) {
        String[] beanNames = context.getBeanNames();
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Class<?> clazz = bean.getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                String basePath = StringUtils.EMPTY;
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

        HandlerMapping handlerMapping = handlerMappingMap.get(req.getRequestURI());

        HandlerAdapter handlerAdapter = handlerAdapterMap.get(handlerMapping);

        ModelAndView modelAndView = handlerAdapter.handler(req, resp, handlerMapping);

        try {
            processDispatchResult(req, resp, modelAndView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView) throws Exception {
        //调用viewResolver的resolveView方法
        if (null == modelAndView) {
            return;
        }
        ViewResolver viewResolver = viewResolverMap.get(modelAndView.getView());
        String result = viewResolver.resolver(modelAndView);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(result);
        resp.getWriter().flush();
    }

}
