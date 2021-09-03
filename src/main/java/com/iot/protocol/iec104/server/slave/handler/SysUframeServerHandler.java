package com.iot.protocol.iec104.server.slave.handler;

import com.iot.protocol.iec104.common.BasicInstruction104;
import com.iot.protocol.iec104.common.Iec104Constant;
import com.iot.protocol.iec104.enums.UControlEnum;
import com.iot.protocol.util.Iec104Util;
import com.iot.protocol.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理U帧的报文--Server端
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@Slf4j
public class SysUframeServerHandler extends ChannelInboundHandlerAdapter {

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
     * 判断是否是 系统报文，U帧只有6字节
     *
     * @param bytes 报文
     * @return boolean
     */
    private boolean isSysInstruction(byte[] bytes) {
        return bytes.length == Iec104Constant.APCI_LENGTH;
    }

    /**
     * 处理U帧
     *
     * @param ctx          上下文
     * @param result       报文
     * @param uControlEnum 控制域固定报文
     */
    private void uInstructionHandler(ChannelHandlerContext ctx, ByteBuf result, UControlEnum uControlEnum) {
        result.readBytes(new byte[result.readableBytes()]);
        byte[] resultBytes = null;
        if (uControlEnum == UControlEnum.TEST) {
            log.info("收到测试指令");
            resultBytes = BasicInstruction104.TEST_REPLY;
        } else if (uControlEnum == UControlEnum.STOP) {
            log.info("收到停止指令");
            resultBytes = BasicInstruction104.STOP_REPLY;
        } else if (uControlEnum == UControlEnum.START) {
            log.info("收到启动指令");
            resultBytes = BasicInstruction104.START_REPLY;
        } else {
            log.info("U报文无效" + uControlEnum);
        }
        if (resultBytes != null) {
            result.writeBytes(resultBytes);
            log.info("回复U报");
            ctx.writeAndFlush(result);
        }
    }
}
