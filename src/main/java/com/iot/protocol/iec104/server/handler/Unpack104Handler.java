package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.common.Iec104Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解决TCP 拆包和沾包的问题
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class Unpack104Handler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        // 记录包头开始的index  
        int beginReader;
        int newDataLength = 0;
        while (true) {
            // 获取包头开始的index  
            beginReader = buffer.readerIndex();
            // 记录一个标志用于重置
            buffer.markReaderIndex();
            // 读到了协议的开始标志，结束while循环  
            if (buffer.readByte() == Iec104Constant.HEAD_DATA) {
                // 标记当前包为新包
                //读取包长度
                byte newDataLengthByte = buffer.readByte();
                newDataLength = newDataLengthByte & 0xFF;
                break;
            }
            continue;
        }

        if (buffer.readableBytes() < newDataLength) {
            buffer.readerIndex(beginReader);
            return;
        }
        newDataLength = newDataLength + 2;
        //恢复指针
        buffer.readerIndex(beginReader);
        ByteBuf data = buffer.readBytes(newDataLength);
        out.add(data);
    }
}
