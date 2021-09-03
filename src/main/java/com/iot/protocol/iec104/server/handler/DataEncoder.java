package com.iot.protocol.iec104.server.handler;


import com.iot.protocol.iec104.core.Encoder104;
import com.iot.protocol.iec104.core.Iec104ThreadLocal;
import com.iot.protocol.util.Iec104Util;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 编码器
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
@Slf4j
public class DataEncoder extends MessageToByteEncoder<MessageDetail> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessageDetail msg, ByteBuf out) throws Exception {
        try {
            byte[] bytes = Encoder104.encoder(msg);
            short accept = 0;
            short send = 2;
            try {
                accept = Iec104ThreadLocal.getControlPool().getAccept();
                send = Iec104ThreadLocal.getControlPool().getSend();
            } catch (Exception e) {

            }
            short terminalAddress = Iec104ThreadLocal.getIec104Config().getTerminalAddress();
            // 替换终端地址 发送序号和接收序号
            byte[] terminalAddressBytes = Iec104Util.getTerminalAddressByte(terminalAddress);
            byte[] control = Iec104Util.getIControl(accept, send);
            for (int i = 0; i < control.length; i++) {
                bytes[i + 2] = control[i];
            }
            bytes[10] = terminalAddressBytes[0];
            bytes[11] = terminalAddressBytes[1];
            log.info(ByteUtil.byteArrayToHexString(bytes));
            out.writeBytes(bytes);
        } catch (Exception e) {
            log.error("", e);
        }

    }

}
