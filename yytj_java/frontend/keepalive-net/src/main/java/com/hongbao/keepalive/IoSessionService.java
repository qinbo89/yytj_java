package com.hongbao.keepalive;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ioSessionService")
public class IoSessionService {
	private static Logger logger = LoggerFactory
			.getLogger(IoSessionService.class);
	public ExecutorService pool = Executors.newFixedThreadPool(1);

	public ExecutorService sendCheckThreadpool = Executors
			.newFixedThreadPool(1);

	public void getIoSession() {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {

					Iterator<IoSession> it = IoSessionUtil.backgroundSessionList
							.iterator();
					while (it.hasNext()) {
						try {
							IoSessionUtil.sessionQueue.put(it.next());
						} catch (InterruptedException e) {
							logger.error("线程异常:" + e.getMessage());
						}
					}
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage(), e1);
					}
				}
			}
		});
	}

	public void checkDataExecute() {
		sendCheckThreadpool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Iterator<IoSession> it = IoSessionUtil.allSessionList
							.iterator();
					while (it.hasNext())
						try {
							IoSessionUtil.allSessionQueue.put(it.next());
						} catch (InterruptedException e) {
							logger.error(e.getMessage(), e);
						}

					try {
						Thread.sleep(30000);
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage(), e1);
					}
				}

			}
		});
	}

	public static void addAllSessionList(IoSession session) {
		if (!IoSessionUtil.allSessionList.contains(session)) {
			IoSessionUtil.allSessionList.add(session);
		}
	}

	public static void removeAllSessionList(IoSession session) {
		IoSessionUtil.allSessionList.remove(session);
		removeBackgroundSession(session);
	}

	public static void addBackgroundSession(IoSession session) {
		if (!IoSessionUtil.backgroundSessionList.contains(session)) {
			IoSessionUtil.backgroundSessionList.add(session);
		}

	}

	public static void removeBackgroundSession(IoSession session) {
		IoSessionUtil.backgroundSessionList.remove(session);
	}
}
