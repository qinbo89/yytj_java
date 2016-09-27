package com.hongbao.keepalive;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

@Service
public class ConnectionServer implements
        ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private final int PORT = 29423;

    @Autowired
    private IoSessionService ioSessionService;

    @Autowired
    private ActSendService actSendService;

    @Autowired
    private CheckAliceService checkAliceService;

    private ApplicationContext applicationContext;

    public void serverStart() throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                        .forName("UTF-8"))));
        MyServerHandler myServerHandler = new MyServerHandler();
        myServerHandler.setWebApplicationContext(applicationContext);
        acceptor.setHandler(myServerHandler);
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 100);
        acceptor.bind(new InetSocketAddress(PORT));

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            serverStart();
            ioSessionService.getIoSession();
            ioSessionService.checkDataExecute();
            actSendService.sendClient();
            actSendService.sendCheckClient();
            checkAliceService.checkClient();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;

    }
}
