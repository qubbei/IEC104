package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.message.MessageDetail;

/**
 * 处理数据
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public interface ChannelHandler {
    void writeAndFlush(MessageDetail detail104);
}
