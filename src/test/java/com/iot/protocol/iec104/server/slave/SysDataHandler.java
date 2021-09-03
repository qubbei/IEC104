package com.iot.protocol.iec104.server.slave;

import com.iot.protocol.iec104.common.BasicInstruction104;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.server.handler.ChannelHandler;
import com.iot.protocol.iec104.server.handler.DataHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SysDataHandler implements DataHandler {

	@Override
	public void handlerAdded(ChannelHandler ctx) throws Exception {
		ctx.writeAndFlush(BasicInstruction104.getGeneralCallReplyDetail104());
	}

	@Override
	public void channelRead(ChannelHandler ctx, MessageDetail Detail104) throws Exception {
		log.info("收到的报文：" + Detail104.toString());
	}

}
