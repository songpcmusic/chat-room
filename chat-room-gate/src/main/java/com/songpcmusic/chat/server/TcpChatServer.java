package com.songpcmusic.chat.server;

import com.songpcmusic.chat.initializer.TcpServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * tcp server
 * <p>
 * Created by Tony on 4/13/16.
 */
@Component("tcpChatServer")
public class TcpChatServer implements ChatServer {

    private Logger logger = LoggerFactory.getLogger(TcpChatServer.class);

    @Value("${server.tcp.port:9000}")
    private int port;

    @Autowired
    private TcpServerInitializer serverInitializer;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private ChannelFuture channelFuture;

//    @PostConstruct
    public void start() throws Exception {
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serverInitializer);

            logger.info("Starting TcpChatServer... Port: " + port);

            channelFuture = b.bind(port).sync();

        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
        }
    }

    public void restart() throws Exception {
        shutdown();
        start();
    }

    public void shutdown() {
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
    }

}
