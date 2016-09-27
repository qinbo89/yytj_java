package com.hongbao.keepalive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("actSendService")
public class ActSendService {
	private static Logger logger = LoggerFactory
			.getLogger(ActSendService.class);

	public ExecutorService pool = Executors.newFixedThreadPool(1);

	public ExecutorService checkPool = Executors.newFixedThreadPool(1);

	public void sendClient() {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						IoSession session = IoSessionUtil.sessionQueue.take();
						if (session != null && !session.isClosing()) {
							session.write(1);
						}
					} catch (InterruptedException e) {
						logger.error("线程异常:" + e.getMessage());
					}
				}
			}
		});
	}

	public void sendCheckClient() {
		checkPool.execute(new Runnable() {
			@Override
			public void run() {
				sendCheckData();
			}
		});
	}

	private void sendCheckData() {
		logger.warn("IoSessionUtil.allSessionQueue="
				+ IoSessionUtil.allSessionQueue.size());
		while (true) {
			IoSession session = null;
			try {
				session = IoSessionUtil.allSessionQueue.take();
			} catch (InterruptedException e) {
				logger.error("session队列异常" + e.getMessage());
				continue;
			}
			if (session != null && session.isConnected()) {// 处于连接状态,发送探测数据给客户端
				session.write(ParseDataUtil.keepAliveData);
				UserSession userSession = null;
				if (!IoSessionUtil.userSessionMap.containsKey(session.getId())) {
					userSession = new UserSession();
					userSession.setSendTime(System.currentTimeMillis());
					userSession.setSession(session);
					IoSessionUtil.userSessionMap.put(session.getId(),
							userSession);
				} else { // IoSessionUtil.userSessionMap
							// 存在这个用户对象,更新发送检测数据的时间
					userSession = IoSessionUtil.userSessionMap.get(session
							.getId());
					userSession.setSendTime(System.currentTimeMillis());
					IoSessionUtil.userSessionMap.put(session.getId(),
							userSession);
				}
			} else {
				IoSessionService.removeAllSessionList(session); // 如果这个session
																// 是异常
				// 就从全局的session列表中删除这个session

			}
		}
	}
}
