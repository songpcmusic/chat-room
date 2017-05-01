package com.songpcmusic.chat.initializer;

import com.songpcmusic.chat.codec.TcpProtoCodec;
import com.songpcmusic.chat.handler.ChatServerHandler;
import com.songpcmusic.chat.handler.HeartbeatServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * TCP服务初始化类
 */
@Component
public class TcpServerInitializer extends ChannelInitializer {

    private static final int READ_IDEL_TIME_OUT = 10; // 读超时
    private static final int WRITE_IDEL_TIME_OUT = 10;// 写超时
    private static final int ALL_IDEL_TIME_OUT = 10; // 所有超时

    @Autowired
    private TcpProtoCodec protoCodec;

    @Autowired
    private ChatServerHandler serverHandler;

    @Override
    protected void initChannel(io.netty.channel.Channel ch) throws Exception {
        ch.pipeline().addLast(protoCodec);
        ch.pipeline().addLast(new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS), serverHandler);
        ch.pipeline().addLast(new HeartbeatServerHandler()); // 2
    }

}
