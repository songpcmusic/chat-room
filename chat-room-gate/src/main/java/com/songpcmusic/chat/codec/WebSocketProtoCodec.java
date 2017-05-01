package com.songpcmusic.chat.codec;

import com.alibaba.fastjson.JSON;
import com.songpcmusic.chat.domain.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WebSocket 协议加解密
 */
@Component
@ChannelHandler.Sharable
public class WebSocketProtoCodec extends MessageToMessageCodec<WebSocketFrame, Protocol> {

    private Logger logger = LoggerFactory.getLogger(TcpProtoCodec.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol protocol, List<Object> list) throws Exception {
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
//        if (protocol.getBody() != null) {
//            byteBuf.writeInt(Protocol.HEADER_LENGTH + protocol.getBody().getBytes().length);
//            byteBuf.writeShort(Protocol.HEADER_LENGTH);
//            byteBuf.writeShort(Protocol.VERSION);
//            byteBuf.writeInt(protocol.getOperation());
//            byteBuf.writeInt(protocol.getExtra());
//            byteBuf.writeBytes(protocol.getBody().getBytes());
//        } else {
//            byteBuf.writeInt(Protocol.HEADER_LENGTH);
//            byteBuf.writeShort(Protocol.HEADER_LENGTH);
//            byteBuf.writeShort(Protocol.VERSION);
//            byteBuf.writeInt(protocol.getOperation());
//            byteBuf.writeInt(protocol.getExtra());
//        }
//
//        list.add(new BinaryWebSocketFrame(byteBuf));

        list.add(new TextWebSocketFrame(JSON.toJSONString(protocol)));

        logger.debug("encode: {}", protocol);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        Protocol protocol = JSON.parseObject(((TextWebSocketFrame) webSocketFrame).text(), Protocol.class);

        list.add(protocol);

        logger.debug("decode: {}", protocol);
    }
}
