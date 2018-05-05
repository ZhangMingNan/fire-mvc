package com.ly.zmn48644.firemvc.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class HandlerAdapter {

    private Map<String, Integer> paramMap;

    public HandlerAdapter(Map<String, Integer> paramMap) {
        this.paramMap = paramMap;
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, HandlerMapping mapping) {
        try {

            Object[] args = null;

            if (paramMap != null) {
                args = new Object[paramMap.size()];

                Map<String, String[]> parameterMap = request.getParameterMap();
                Class<?>[] parameterTypes = mapping.getMethod().getParameterTypes();
                for (String parameter : parameterMap.keySet()) {
                    if (paramMap.containsKey(parameter)) {

                        int index = paramMap.get(parameter);
                        String value = Arrays.toString(parameterMap.get(parameter)).replaceAll("\\[|\\]", "").replaceAll("\\s", "");
                        args[index] = caseTo(value, parameterTypes[index]);


                    }

                }

            }


            Object result = mapping.getMethod().invoke(mapping.getObject(), args);

            if (result instanceof ModelAndView) {
                return (ModelAndView) result;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }


    private Object caseTo(String value, Class<?> parameterType) {
        if (parameterType == String.class) {
            return value;
        } else if (parameterType == Integer.class || parameterType == int.class) {
            return Integer.valueOf(value);
        }
        return value;
    }
}
