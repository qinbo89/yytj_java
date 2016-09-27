package com.hongbao.keepalive;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.mina.core.session.IoSession;



public class IoSessionUtil {

	public static List<IoSession> backgroundSessionList = new LinkedList<IoSession>();

	public static List<IoSession> allSessionList = new LinkedList<IoSession>();

	public static BlockingQueue<IoSession> allSessionQueue = new ArrayBlockingQueue<IoSession>(
			2000);

	public static Map<Long, UserSession> userSessionMap = new LinkedHashMap<Long, UserSession>();

	public static BlockingQueue<IoSession> sessionQueue = new ArrayBlockingQueue<IoSession>(
			1000);

}
