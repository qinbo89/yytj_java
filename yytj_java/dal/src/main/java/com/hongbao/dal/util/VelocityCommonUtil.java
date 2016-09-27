package com.hongbao.dal.util;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hongbao.dal.page.PageInfo;
import com.hongbao.utils.WebUtils;

public class VelocityCommonUtil {
	protected static Logger log = LoggerFactory
			.getLogger(VelocityCommonUtil.class);

	public static String getAbsoluteURL() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String contextPath = request.getContextPath();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String scheme = request.getScheme();
		String portFix = "";
		if (serverPort != 80) {
			portFix = ":" + serverPort;
		}
		return scheme + "://" + serverName + portFix + contextPath;
	}

	public static String getBasePath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String contextPath = request.getContextPath();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String scheme = request.getScheme();
		String portFix = "";
		if (serverPort != 80) {
			portFix = ":" + serverPort;
		}
		return scheme + "://" + serverName + portFix + contextPath;
	}

	/**
	 * XXX改成从属性文件取
	 * 
	 * @return
	 */
	public static String getStaticUrl() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String contextPath = request.getContextPath();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String scheme = request.getScheme();
		String portFix = "";
		if (serverPort != 80) {
			portFix = ":" + serverPort;
		}
		return scheme + "://" + serverName + portFix + contextPath;

	}

	public static String getDownloadUrlFromPGY(String url) {
		String html = null;
		try {
			html = WebUtils.doGet(url, new HashMap<String, String>());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return url;
		}
		return "http://www.pgyer.com/app/install/"
				+ StringUtils.substringBetween(html, "aKey = '", "',");
	}

	public static Map<String, String> queryStringToMap(String queryString) {
		Map<String, String> map = new LinkedHashMap<String, String>();

		if (StringUtils.isBlank(queryString)) {
			return map;
		}
		String[] kava = queryString.split("&");
		if (kava != null && kava.length > 0) {
			for (String kv : kava) {
				if (StringUtils.isNotBlank(kv)) {
					String[] kva = kv.split("=");
					if (kva != null && kva.length == 1) {
						map.put(kva[0], null);
					}
					if (kva != null && kva.length >= 1) {
						map.put(kva[0], kva[1]);
					}

				}

			}
		}
		return map;
	}

	public static String buildQuery(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=").append(value);
			}
		}

		return query.toString();
	}

	public String pageInfo(PageInfo pageInfo) {
		if (pageInfo == null) {
			return "";
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		Map<String, String> params = new LinkedHashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		String parameterName = null;
		String parameterValue = null;
		if (parameterNames == null) {
		} else {
			while (parameterNames.hasMoreElements()) {
				parameterName = parameterNames.nextElement();
				parameterValue = request.getParameter(parameterName);
				params.put(parameterName, parameterValue);
			}
		}
		if (params.get("pageSize") == null) {
			params.put("pageSize", PageInfo.DEFAULT_PAGE_SIZE + "");
		}
		params.remove("pageNum");

		String getRequestURI = request.getRequestURI();
		String url = getRequestURI + "?" + buildQuery(params);

		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"pageInfoWrap\" style=\"height: 30px; vertical-align: middle; line-height: 30px; margin-top: 20px;\">\n");
		sb.append("共有" + pageInfo.getTotalCount() + "条,"
				+ pageInfo.getPageNum() + "/" + pageInfo.getTotalPage()
				+ "页 \n");
		sb.append("<span style=\"width: 30px; height: 30px; background-color: #f0f0f0; border: 1px solid #e2e2e2; display: inline-block; text-align: center;\"><a style=\"width: 100%;height: 100%;display: inline-block;\" class=\"emptylink pagegolink\" href=\""
				+ url + "&pageNum=" + (1) + "\"><<</a></span>\n");
		if (pageInfo.getPageNum() - 1 >= 1) {
			sb.append("<span style=\"width: 30px; height: 30px; background-color: #f0f0f0; border: 1px solid #e2e2e2; display: inline-block; text-align: center;\"><a style=\"width: 100%;height: 100%;display: inline-block;\" class=\"emptylink pagegolink\" href=\""
					+ url
					+ "&pageNum="
					+ (pageInfo.getPageNum() - 1)
					+ "\"><</a></span> \n");
		}
		if (pageInfo.getPageNum() + 1 <= pageInfo.getTotalPage()) {
			sb.append("<span style=\"width: 30px; height: 30px; background-color: #f0f0f0; border: 1px solid #e2e2e2; display: inline-block; text-align: center;\"><a style=\"width: 100%;height: 100%;display: inline-block;\" class=\"emptylink pagegolink\" href=\""
					+ url
					+ "&pageNum="
					+ (pageInfo.getPageNum() + 1)
					+ "\">></a></span> \n");
		}
		sb.append("<span style=\"width: 30px; height: 30px; background-color: #f0f0f0; border: 1px solid #e2e2e2; display: inline-block; text-align: center;\"><a style=\"width: 100%;height: 100%;display: inline-block;\" class=\"emptylink pagegolink\" href=\""
				+ url
				+ "&pageNum="
				+ (pageInfo.getTotalPage())
				+ "\">>></a></span> \n");

		sb.append(" 去第 <input class=\"pageInfoGoTo\" type=\"text\" style=\"width: 30px; text-align: center;\">页<input type=\"button\" class=\"btn   btn-danger   \" value=\"确定\"  onclick=\"location.href='"
				+ url
				+ "&pageNum='"
				+ "+$(this).closest('.pageInfoWrap').find('.pageInfoGoTo').eq(0).val()  \"  "
				+ "style=\"height: 30px; width: 60px; margin-left: 10px;\" />\n");
		sb.append("</div>");

		return sb.toString();
	}

	/**
	 * 分转化成元
	 * 
	 * @param amount
	 * @return
	 */
	public double fen2Yuan(Integer amount) {
		if (amount == null) {
			return 0.00;
		} else {
			return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100))
					.doubleValue();
		}
	}

	/**
	 * 分转化成元
	 * 
	 * @param amount
	 * @return
	 */
	public double longFen2Yuan(Long amount) {
		if (amount == null) {
			return 0.00;
		} else {
			return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100))
					.doubleValue();
		}
	}
}
