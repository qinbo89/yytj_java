package com.hongbao.dal.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.util.RuntimeServicesAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTML转义输出
 */
public class VelocityEscapeHtmlOutput 
    implements ReferenceInsertionEventHandler, RuntimeServicesAware
{
    protected Logger log = LoggerFactory.getLogger(getClass());

    
    private RuntimeServices rs = null;
     
    public Object referenceInsert(String reference, Object value) 
    {
        if("$screen_content".equals(reference)==false && value instanceof String && value.toString().toUpperCase().contains("SCRIPT")) {
            log.error("XSS攻击");
            return StringEscapeUtils.escapeHtml4(value.toString());
        }
        // 其它默认转义
        return value;
    }
     
    public void setRuntimeServices(RuntimeServices rs) 
    {
        this.rs = rs;
    }
     
    protected RuntimeServices getRuntimeServices()
    {
        return this.rs;
    }
     
    /**
     * 转义HTML字符串
     * @param str
     * @return
     */
    private static Object escapeHtml(Object value)
    {
        if(value == null)
        {
            return null;
        }
         
        if(!(value instanceof String))
        {
            return value;
        }
         
        String str = value.toString();
        StringBuilder sb = new StringBuilder(str.length() + 30);
         
        for(int i = 0, len = str.length(); i < len; i++)
        {
            char c = str.charAt(i);
            // 去除不可见字符
            if((int)c < 32)
            {
                continue;
            }
             
            switch(c)
            {
                case '<':
                    sb.append("&#60;");
                    break;
                case '>':
                    sb.append("&#62;");
                    break;
                case '&':
                    sb.append("&#38;");
                    break;
                case '"':
                    sb.append("&#34;");
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                case '/':
                    sb.append("&#47;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
         
        str = null;
         
        return sb.toString();
    }
}