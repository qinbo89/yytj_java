package com.hongbao.web.controller.base;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class H5BaseController {
    protected Logger log = LoggerFactory.getLogger(getClass());

    public void addFlashAttribute(RedirectAttributes attrs, Map<String, Object> param) {
        if (attrs != null && param != null) {
            Iterator<Entry<String, Object>> it = param.entrySet().iterator();
            while (it != null && it.hasNext()) {
                Entry<String, Object> entry = it.next();
                if (entry != null) {
                    attrs.addFlashAttribute(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}
