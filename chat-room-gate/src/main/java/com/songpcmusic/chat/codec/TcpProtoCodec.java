package com.songpcmusic.chat.codec;

import com.songpcmusic.chat.domain.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TCP协议加解密
 */
@Component
@ChannelHandler.Sharable
public class TcpProtoCodec extends MessageToMessageCodec<ByteBuf, Protocol> {

    private Logger logger = LoggerFactory.getLogger(TcpProtoCodec.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Protocol protocol, List<Object> list) throws Exception {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        if (protocol.getBody() != null) {
            byteBuf.writeInt(Protocol.HEADER_LENGTH + protocol.getBody().getBytes().length);
            byteBuf.writeShort(Protocol.HEADER_LENGTH);
            byteBuf.writeShort(Protocol.VERSION);
            byteBuf.writeInt(protocol.getOperation());
            byteBuf.writeInt(protocol.getExtra());
            byteBuf.writeBytes(protocol.getBody().getBytes());
        } else {
            byteBuf.writeInt(Protocol.HEADER_LENGTH);
            byteBuf.writeShort(Protocol.HEADER_LENGTH);
            byteBuf.writeShort(Protocol.VERSION);
            byteBuf.writeInt(protocol.getOperation());
            byteBuf.writeInt(protocol.getExtra());
        }

        list.add(byteBuf);

        logger.debug("encode: {}", protocol);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Protocol protocol = new Protocol();
        protocol.setPacketLength(byteBuf.readInt());
        protocol.setHeaderLength(byteBuf.readShort());
        protocol.setVersion(byteBuf.readShort());
        protocol.setOperation(byteBuf.readInt());
        protocol.setExtra(byteBuf.readInt());
        if (protocol.getPacketLength() > protocol.getHeaderLength()) {
            byte[] bytes = new byte[protocol.getPacketLength() - protocol.getHeaderLength()];
            byteBuf.readBytes(bytes);
            protocol.setBody(new String(bytes));
        }

        list.add(protocol);

        logger.debug("decode: {}", protocol);
    }
}
