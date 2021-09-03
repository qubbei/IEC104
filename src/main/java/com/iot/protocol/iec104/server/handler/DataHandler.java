package com.iot.protocol.iec104.server.handler;

import com.iot.protocol.iec104.message.MessageDetail;

/**
 * 数据处理
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public interface DataHandler {

    /**
     * 建立连接
     *
     * @param ctx 上下文对象
     * @throws Exception e
     */
    void handlerAdded(ChannelHandler ctx) throws Exception;

    /**
     * 收到消息
     *
     * @param ctx       上下文对象
     * @param detail104 报文构造体对象
     * @throws Exception e
     */
    void channelRead(ChannelHandler ctx, MessageDetail detail104) throws Exception;
}
