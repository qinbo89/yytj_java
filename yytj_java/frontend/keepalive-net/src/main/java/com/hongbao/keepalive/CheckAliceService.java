package com.hongbao.keepalive;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("checkAliceService")
public class CheckAliceService {
	private static Logger logger = LoggerFactory
			.getLogger(CheckAliceService.class);

	public ExecutorService pool = Executors.newFixedThreadPool(1);

	public void checkClient() {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {

					Collection<UserSession> userSessions = IoSessionUtil.userSessionMap
							.values();
					for (UserSession userSession : userSessions) {
						Long sessionId = userSession.getSession().getId();
						if (userSession == null
								|| userSession.getSession() == null) {
							continue;
						}
						if (userSession.getSendTime() == 0
								|| userSession.getSendTime() == null) {
							continue;
						}
						if (userSession.getLastUpdateTime() != null
								&& userSession.getLastUpdateTime() > 0) {// 发送出去的数据送huil
							if ((System.currentTimeMillis() - userSession
									.getLastUpdateTime()) > 1000 * 60 * 5) {
								IoSessionUtil.userSessionMap.remove(sessionId);
								IoSessionService
										.removeAllSessionList(userSession
												.getSession());
								logger.warn("check sessionId=" + sessionId
										+ " not getLastUpdateTime past 5 min ");
								userSession.getSession().close(true);
							}
						} else {
							if (userSession.getSendTime() != null
									&& System.currentTimeMillis()
											- userSession.getSendTime() > 1000 * 60 * 5) {
								IoSessionUtil.userSessionMap.remove(sessionId);
								IoSessionService
										.removeAllSessionList(userSession
												.getSession());
								logger.warn("check sessionId=" + sessionId
										+ " not sendtime  past 5 min ");
								userSession.getSession().close(true);
							}
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
				}

			}
		});
	}
}
