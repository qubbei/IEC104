package com.iot.protocol.iec104.server.master.handler;


import com.iot.protocol.iec104.core.CachedThreadPool;
import com.iot.protocol.iec104.core.ControlManageUtil;
import com.iot.protocol.iec104.core.Iec104ThreadLocal;
import com.iot.protocol.iec104.core.ScheduledTaskPool;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.server.handler.ChannelHandlerImpl;
import com.iot.protocol.iec104.server.handler.DataHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

/**
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class Iec104ClientHandler extends SimpleChannelInboundHandler<MessageDetail> {

	private DataHandler dataHandler;

	public Iec104ClientHandler(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 启动成功后一直发启动链路命令
		Iec104ThreadLocal.setScheduledTaskPool(new ScheduledTaskPool(ctx));
		Iec104ThreadLocal.getScheduledTaskPool().sendStartFrame();
		Iec104ThreadLocal.setControlPool(new ControlManageUtil(ctx).setFrameAmountMax(Iec104ThreadLocal.getIec104Config().getFrameAmountMax()));
		Iec104ThreadLocal.getControlPool().startSendFrameTask();

		if (dataHandler != null) {
			CachedThreadPool.getCachedThreadPool().execute(() -> {
                try {
                    dataHandler.handlerAdded(new ChannelHandlerImpl(ctx));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
	}
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, MessageDetail detail104) throws IOException {
		if (dataHandler != null) {
			CachedThreadPool.getCachedThreadPool().execute(() -> {
                try {
                    dataHandler.channelRead(new ChannelHandlerImpl(ctx), detail104);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    	}
	}


}
