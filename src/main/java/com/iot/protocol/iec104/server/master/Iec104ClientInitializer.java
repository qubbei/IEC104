package com.iot.protocol.iec104.server.master;

import com.iot.protocol.iec104.config.Iec104Config;
import com.iot.protocol.iec104.core.Iec104ThreadLocal;
import com.iot.protocol.iec104.server.handler.*;
import com.iot.protocol.iec104.server.master.handler.Iec104ClientHandler;
import com.iot.protocol.iec104.server.master.handler.SysUframeClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 104协议 处理链
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@Setter
@Accessors(chain = true)
public class Iec104ClientInitializer extends ChannelInitializer<SocketChannel> {


    private DataHandler dataHandler;

    private Iec104Config iec104Config;

    /**
     * 初始化处理链
     */
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        if (iec104Config == null) {
            Iec104ThreadLocal.setIec104Config(iec104Config);
        } else {
            Iec104ThreadLocal.setIec104Config(new Iec104Config());
        }
        ChannelPipeline pipeline = ch.pipeline();
        // 沾包拆包工具
        pipeline.addLast("unpack", new Unpack104Handler());
        // 数据检查工具
        pipeline.addLast("check", new Check104Handler());
//		/拦截 U帧处理器 
        pipeline.addLast("uFrame", new SysUframeClientHandler());
        //拦截 S帧处理器
        pipeline.addLast("sFrame", new SysSframeHandler());
        //编码器
        pipeline.addLast("byteEncoder", new BytesEncoder());
        //编码器
        pipeline.addLast("encoder", new DataEncoder());

//		 解码器
        pipeline.addLast("decoder", new DataDecoder());
        pipeline.addLast("handler", new Iec104ClientHandler(dataHandler));
    }
}
