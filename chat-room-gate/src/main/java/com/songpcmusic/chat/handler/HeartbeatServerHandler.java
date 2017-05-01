package com.songpcmusic.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(HeartbeatServerHandler.class);

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {  // 2
			IdleStateEvent event = (IdleStateEvent) evt;  
			String type = "";
			if (event.state() == IdleState.READER_IDLE) {
				LOGGER.info("read idle");
//				ctx.close();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				LOGGER.info("write idle");
//				ctx.close();
			} else if (event.state() == IdleState.ALL_IDLE) {
				LOGGER.info("all idle");
			}
		} else {
		    super.userEventTriggered(ctx, evt);
        }
	}
}