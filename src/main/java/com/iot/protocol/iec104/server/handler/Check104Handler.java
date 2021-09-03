package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.common.Iec104Constant;
import com.iot.protocol.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 检查104报文（header、最小报文长度6）
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
@Slf4j
public class Check104Handler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        log.info("接收到的报文: {}", ByteUtil.byteArrayToHexString(bytes));
        if (bytes.length < Iec104Constant.APCI_LENGTH || bytes[0] != Iec104Constant.HEAD_DATA) {
            log.error("无效的报文");
            ReferenceCountUtil.release(result);
        } else {
            result.writeBytes(bytes);
            ctx.fireChannelRead(msg);
        }
    }
}
