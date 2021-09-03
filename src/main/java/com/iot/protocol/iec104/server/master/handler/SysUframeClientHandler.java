package com.iot.protocol.iec104.server.master.handler;

import com.iot.protocol.iec104.common.Iec104Constant;
import com.iot.protocol.iec104.core.Iec104ThreadLocal;
import com.iot.protocol.util.Iec104Util;
import com.iot.protocol.iec104.enums.UControlEnum;
import com.iot.protocol.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理U帧的报文--Client端
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@Slf4j
public class SysUframeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        if (isSysInstruction(bytes)) {
            UControlEnum uControlEnum = Iec104Util.getUcontrol(ByteUtil.getByte(bytes, 2, 4));
            if (uControlEnum != null) {
                uInstructionHandler(ctx, result, uControlEnum);
                return;
            }
        }
        result.writeBytes(bytes);
        ctx.fireChannelRead(result);
    }

    /**
     * 判断是否是 【系统报文】,U帧只有6字节
     *
     * @param bytes 报文
     * @return true-是 false-否
     */
    private boolean isSysInstruction(byte[] bytes) {
        return bytes.length == Iec104Constant.APCI_LENGTH;
    }

    /**
     * 处理U帧
     *
     * @param ctx          上下文
     * @param result       byteBuf
     * @param uControlEnum U报文类型
     */
    private void uInstructionHandler(ChannelHandlerContext ctx, ByteBuf result, UControlEnum uControlEnum) {
        result.readBytes(new byte[result.readableBytes()]);
        if (uControlEnum == UControlEnum.TEST_REPLY) {
            log.info("收到测试确认指令");
            Iec104ThreadLocal.getScheduledTaskPool().sendGeneralCall();
        } else if (uControlEnum == UControlEnum.STOP_REPLY) {
            log.info("收到停止确认指令");
        } else if (uControlEnum == UControlEnum.START_REPLY) {
            log.info("收到启动指令确认指令");
            Iec104ThreadLocal.getScheduledTaskPool().stopSendStartFrame();
            Iec104ThreadLocal.getScheduledTaskPool().sendTestFrame();
        } else {
            log.error("U报文无效");
        }
    }
}
