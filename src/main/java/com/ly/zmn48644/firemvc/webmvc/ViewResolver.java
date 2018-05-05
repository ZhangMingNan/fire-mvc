package com.ly.zmn48644.firemvc.webmvc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class ViewResolver {

    private String view;

    private File template;

    public String resolver(ModelAndView modelAndView) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String line : Files.readLines(template, Charsets.UTF_8)) {
                for (String key : modelAndView.getModel().keySet()) {
                    line = StringUtils.replace(line, "${" + key + "}", modelAndView.getModel().get(key).toString());
                }
                sb.append(line);
            }
        } catch (IOException e) {

        }
        return sb.toString();
    }

    public ViewResolver(String view, File template) {
        this.view = view;
        this.template = template;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }
}
