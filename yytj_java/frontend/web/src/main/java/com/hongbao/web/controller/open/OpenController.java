package com.hongbao.web.controller.open;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hongbao.utils.SHA1;


/**
 * 类OpenController.java的实现描述：TODO 类实现描述
 * 
 * @author lv 2014年11月12日 下午4:27:24
 */
@Controller
public class OpenController {

	@RequestMapping("/weixin")
	public void weixin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		String[] str = { "1qahsd343bbngw21sadsd7616ghgyh1", timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes())
				.toLowerCase();

		// 确认请求来至微信
		if (digest.equals(signature)) {
			response.getWriter().print(echostr);
		}
	}

}
