package com.hongbao.service.support;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hongbao.dal.log.HbLogContextMgr;
import com.hongbao.dal.util.CustomizedPropertyConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ResponseHolderFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(ResponseHolderFilter.class);



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("res filter init...");
        ServletContext sc = filterConfig.getServletContext();
        WebApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
       

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HbLogContextMgr.startStack((HttpServletRequest) request);
        log.info("brefore request ...");
        try {
            ResponseHolder.set(response);
            chain.doFilter(request, response);
        } finally {
            ResponseHolder.clear();
            log.info("reponse clear1 ...");
            HbLogContextMgr.endStack((HttpServletRequest) request,(HttpServletResponse) response);
        }
    }

    @Override
    public void destroy() {
        log.error("reponse clear2 ...");
        ResponseHolder.clear();
    }

}
