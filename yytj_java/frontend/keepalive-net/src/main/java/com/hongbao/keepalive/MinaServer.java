package com.hongbao.keepalive;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

	/**
	 * 16 We will use a port above 1024 to be able to launch the server with a
	 * 17 standard user 18
	 */

	private static final int PORT = 29423;

	/**
	 * 22 The server implementation. It's based on TCP, and uses a logging
	 * filter 23 plus a text line decoder. 24
	 */

	public static void main(String[] args) throws IOException {

		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory()));
		acceptor.setHandler(new MyServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 1000);
		acceptor.bind(new InetSocketAddress(PORT));

	}

}
