package com.hongbao.service.test;

import com.hongbao.dal.menu.Button;
import com.hongbao.dal.menu.Menu;
import com.hongbao.service.util.WeixinUtil;
import com.hongbao.service.weixin.constant.ConstantWeChat;
import com.tencent.entity.AccessToken;

import net.sf.json.JSONObject;

public class MenuTest {

	public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	// 第三方用户唯一凭证
	public static String appId = "wx55cee53e0b96179c";
	// 第三方用户唯一凭证密钥
	public static String appSecret = "506145ccc3f4a1b6fa21ac96a7003c80";
	
	public static void main(String[] args) {

			// 调用接口创建菜单
			int result = createMenu(JSONObject.fromObject(getMenu()).toString());

			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
	}
	
	public static Integer createMenu(String jsonMenu) {
		int result = 0;
		String token = "";
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		token = at.getToken();
		if (token != null) {
			// 拼装创建菜单的url
			String url = MENU_CREATE.replace("ACCESS_TOKEN", token);
			// 调用接口创建菜单
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", jsonMenu);

			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					System.out.println("创建菜单失败 errcode:" + jsonObject.getInt("errcode")
							+ "，errmsg:" + jsonObject.getString("errmsg"));
				}
			}
		}
		return result;
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		Button btn11 = new Button();
		btn11.setName("百度");
		btn11.setType("click");
		btn11.setType("view");
		btn11.setUrl("http://www.baidu.com");

		Button btn12 = new Button();
		btn12.setName("搜索");
		btn12.setType("click");
		btn12.setKey("12");

		Button btn13 = new Button();
		btn13.setName("博客");
		btn13.setType("view");
		btn13.setUrl("http://liuxiaohu.cn");

		Button btn14 = new Button();
		btn14.setName("历史上的今天");
		btn14.setType("click");
		btn14.setKey("14");

		Button btn21 = new Button();
		btn21.setName("歌曲点播");
		btn21.setType("click");
		btn21.setKey("21");

		Button btn22 = new Button();
		btn22.setName("经典游戏");
		btn22.setType("click");
		btn22.setKey("22");

		Button btn23 = new Button();
		btn23.setName("美女电台");
		btn23.setType("click");
		btn23.setKey("23");

		Button btn24 = new Button();
		btn24.setName("人脸识别");
		btn24.setType("click");
		btn24.setKey("24");

		Button btn25 = new Button();
		btn25.setName("聊天唠嗑");
		btn25.setType("click");
		btn25.setKey("25");

		Button btn31 = new Button();
		btn31.setName("Q友圈");
		btn31.setType("click");
		btn31.setKey("31");

		Button btn32 = new Button();
		btn32.setName("电影排行榜");
		btn32.setType("click");
		btn32.setKey("32");

		Button btn33 = new Button();
		btn33.setName("幽默笑话");
		btn33.setType("click");
		btn33.setKey("33");

		Button mainBtn1 = new Button();
		mainBtn1.setName("公司服务");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });

		Button mainBtn2 = new Button();
		mainBtn2.setName("夺宝宝岛");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		Button mainBtn3 = new Button();
		mainBtn3.setName("个人中心");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是"更多体验"，而直接是"幽默笑话"，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
