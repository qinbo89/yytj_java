package com.hongbao.keepalive;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.hongbao.service.user.UserLocusService;

public class MyServerHandler extends IoHandlerAdapter {

	private ApplicationContext webApplicationContext;

	private static Logger logger = LoggerFactory
			.getLogger(MyServerHandler.class);

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info(session.getId() + "   " + session.getCreationTime());
		logger.info("threadId:" + Thread.currentThread().getId()
				+ ",threadName=" + Thread.currentThread().getName());
		// session.write(session.getId());
		IoSessionService.addAllSessionList(session);// 添加session到
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error(cause.getMessage(), cause);
		IoSessionService.removeAllSessionList(session);
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		UserLocusService userLocusService = (UserLocusService) webApplicationContext
				.getBean("userLocusService");

		String userId = ParseDataUtil.getUserId(str);
		Long lUserId= new Long(userId);
		String sigin = ParseDataUtil.getSigin(str);
		String checkSign = userLocusService.getLastLoginSign(lUserId);
		logger.warn("str=" + str);
		logger.warn("checkSign=" + checkSign);
		logger.warn("sigin=" + sigin);
		if (!StringUtils.equals(checkSign, sigin)) {
			IoSessionService.removeAllSessionList(session);
			logger.warn("received valid data force connection close ...");
			session.close(true);
			return;
		}
		String cmd = ParseDataUtil.getBzdata(str);
		if (StringUtils.equalsIgnoreCase(cmd, ParseDataUtil.cmd_sw_front_back)) {// 手机端服务从前台到后台
			IoSessionService.addBackgroundSession(session);
		}
		if (StringUtils.equalsIgnoreCase(cmd, ParseDataUtil.cmd_sw_back_front)) {// 手机端服务从后台到前台
			IoSessionService.removeBackgroundSession(session);
		}
		if (StringUtils.equalsIgnoreCase(cmd, ParseDataUtil.keepAliveData)) {// 接受的数据是01,探测的长连接
			UserSession userSession = IoSessionUtil.userSessionMap.get(session
					.getId());
			if (!IoSessionUtil.allSessionList.contains(session)) {
				IoSessionService.addAllSessionList(session);
			}
			if (userSession != null) {
				userSession.setLastUpdateTime(System.currentTimeMillis());
			} else {
				userSession = new UserSession();
				userSession.setSession(session);
				userSession.setLastUpdateTime(System.currentTimeMillis());
				IoSessionUtil.userSessionMap.put(session.getId(), userSession);
			}
		}
		logger.info("received rigth data " + str);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		IoSessionService.removeAllSessionList(session);
		session.close(true);
	}

	public ApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public void setWebApplicationContext(
			ApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

}
