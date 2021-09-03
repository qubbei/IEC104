package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.core.Decoder104;
import com.iot.protocol.iec104.core.Iec104ThreadLocal;
import com.iot.protocol.util.Iec104Util;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * 解码器
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class DataDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		short send = Iec104Util.getSend(ByteUtil.getByte(data, 2, 4));
		short accept = Iec104Util.getAccept(ByteUtil.getByte(data, 2, 4));
		Iec104ThreadLocal.getControlPool().setAccept(send);
		MessageDetail Detail104 = Decoder104.decoder(data);
		out.add(Detail104);
	}
}
