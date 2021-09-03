package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.message.MessageDetail;
import io.netty.channel.ChannelHandlerContext;

/**
 * 实现一个自定义发消息的类
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class ChannelHandlerImpl implements ChannelHandler {

    private final ChannelHandlerContext ctx;

    public ChannelHandlerImpl(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void writeAndFlush(MessageDetail detail104) {
        ctx.channel().writeAndFlush(detail104);
    }
}
