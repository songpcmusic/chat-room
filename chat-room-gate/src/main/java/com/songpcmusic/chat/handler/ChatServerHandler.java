package com.songpcmusic.chat.handler;

import com.songpcmusic.chat.domain.Protocol;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 消息处理类
 */
@Sharable
@Service()
@Scope("prototype")
public class ChatServerHandler extends SimpleChannelInboundHandler<Protocol> {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerHandler.class);

    /**
     * 客户端连接
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 添加
        LOG.info("客户端{}与服务端连接开启", ctx.channel().id());
    }

    /**
     * 客户端关闭
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 移除
        ctx.close();
        LOG.info("客户端与服务端连接关闭");
    }

    /**
     * 读取消息
     *
     * @param ctx   通道上下文
     * @param protocol 协议
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol protocol) throws Exception {
        LOG.info("收到消息，{}", protocol);
        ctx.writeAndFlush(protocol);
    }

    /**
     * 异常消息
     *
     * @param ctx   通道上下文
     * @param cause 线程
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("异常消息", cause);
        ctx.close();
    }

}
