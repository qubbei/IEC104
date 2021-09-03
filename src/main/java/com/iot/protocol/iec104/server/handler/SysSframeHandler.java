package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.common.Iec104Constant;
import com.iot.protocol.util.ByteUtil;
import com.iot.protocol.util.Iec104Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理S帧报文
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
@Slf4j
public class SysSframeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        if (isSysInstruction(bytes)) {
            log.info("收到S帧 getAccept-> " + Iec104Util.getAccept(ByteUtil.getByte(bytes, 2, 4)));
            ReferenceCountUtil.release(result);
            return;
        }
        result.writeBytes(bytes);
        ctx.fireChannelRead(result);
    }

    /**
     * 判断是否是 系统报文
     *
     * @param bytes 报文
     * @return boolean
     */
    private boolean isSysInstruction(byte[] bytes) {
        if (bytes.length != Iec104Constant.APCI_LENGTH) {
            return false;
        }
        // 判断S帧
        return bytes[Iec104Constant.ACCEPT_LOW_INDEX] == 1 && bytes[Iec104Constant.ACCEPT_HIGH_INDEX] == 0;
    }
}
