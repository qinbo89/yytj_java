/**
 * 
 */
package com.hongbao.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean 工具类
 * 
 * @author tangss
 * 
 * @2013-4-8 @上午10:14:03
 */
public class BeanUtils {

	private final static Logger log = LoggerFactory.getLogger(BeanUtils.class);

	private String a = "12321312";
	
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        System.out.println(  BeanUtilsBean.getInstance().getProperty(new BeanUtils(), "a"));
        System.out.println(  PropertyUtils.getProperty(new BeanUtils(), "a"));
    }
    
    public static List<Map<String, Object>> beanListToMapLsit(List<? extends Object>beanList) {
        List<Map<String, Object>>  mapBeanList = new ArrayList<Map<String, Object>>();
        if(CollectionUtils.isEmpty(beanList)){
            return mapBeanList;
        }
        for (Object object : beanList) {
            try {
                mapBeanList.add(PropertyUtils.describe(object));
            } catch (Exception e) {
                log.error("bean to queryMap failed! ", e);
            }
        }
        return mapBeanList;
    }
    public static <T> List<T> beanListToLsit(List<? extends Object>beanList,Class<T> clazz) {
        List<T>  newBeanList = new ArrayList<T>();
        if(CollectionUtils.isEmpty(beanList)){
            return newBeanList;
        }
        for (Object object : beanList) {
            try {
                T destObj = clazz.newInstance();
                PropertyUtils.copyProperties(destObj, object);
                newBeanList.add(destObj);
            } catch (Exception e) {
                log.error("bean to queryMap failed! ", e);
            }
        }
        return newBeanList;
    }
	/**
	 * bean 转换 Map<String,Object>
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object bean) {
		try {
			return PropertyUtils.describe(bean);
		} catch (Exception e) {
			log.error("bean to queryMap failed! ", e);
			return null;
		}
	}
	
	/**
	 * bean 复制
	 * @param srcBean bean 源对象
	 * @param destClass 目标对象class
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static<T> T beanCopy(Object srcBean,Class destClass){
		if(srcBean == null){
			return null;
		}
		try {
			Object destObj = destClass.newInstance();
			PropertyUtils.copyProperties(destObj, srcBean);
			return (T) destObj;
		} catch (Exception e) {
			throw new RuntimeException("bean copy error! ",e);
		}
	}
	/**
	 * bean 复制
	 * @param srcBean bean 源对象
	 * @param destBean 目标对象
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void beanCopy(Object srcBean,Object destBean){
		if(srcBean == null){
			return;
		}
		try {
			PropertyUtils.copyProperties(destBean, srcBean);
		} catch (Exception e) {
			throw new RuntimeException("bean copy error! ",e);
		}
	}

	/**
	 * 对象深度复制，条件各引用对象必须实现序列化
	 * @param srcObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> T objDepthCopy(T srcObj) {
		T destObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(srcObj);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			destObj = (T) ois.readObject();
		} catch (Exception e) {
			log.error("depth object copy failed! ", e);
			return null;
		}
		return destObj;
	}

    public static Map<String, Object> beanToMapNotNull(Object srcBean) {
        try {
            Map<String, Object> map = PropertyUtils.describe(srcBean);
            Set<String> key = new HashSet<String>(map.keySet());
            for (String string : key) {
                if (map.get(string) == null) {
                    map.remove(string);
                }
            }
            return map;
        } catch (Exception e) {
            log.error("bean to queryMap failed! ", e);
            return null;
        }
    }
}
