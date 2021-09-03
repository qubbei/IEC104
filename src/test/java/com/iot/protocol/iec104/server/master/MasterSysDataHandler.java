package com.iot.protocol.iec104.server.master;

import com.iot.protocol.iec104.common.BasicInstruction104;
import com.iot.protocol.iec104.core.CachedThreadPool;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.server.handler.ChannelHandler;
import com.iot.protocol.iec104.server.handler.DataHandler;
import com.iot.protocol.util.Iec104Util;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MasterSysDataHandler implements DataHandler {

    @Override
    public void handlerAdded(ChannelHandler ctx) throws Exception {
        Runnable runnable = () -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("___________________________");
            ctx.writeAndFlush(BasicInstruction104.getGeneralCallDetail104());
        };
        CachedThreadPool.getCachedThreadPool().execute(runnable);
//		ctx.writeAndFlush(BasicInstruction104.getEndGeneralCallDetail104());
    }

    @Override
    public void channelRead(ChannelHandler ctx, MessageDetail Detail104) throws Exception {
//		log.info("启动字符：" + Detail104.getStart());
//		log.info("字节长度：" + Detail104.getApduLength());
        byte[] control = Detail104.getControl();
        log.info("控制域：Accept->{}, Send->{}", Iec104Util.getAccept(control), Iec104Util.getSend(control));
        log.info(Detail104.toString());
//		log.info("类型标识：" + Detail104.getTypeIdentifier().getValue());
//		log.info("可变结构限定词：" + Detail104.isContinuous());
//		log.info("数据长度：" + Detail104.getMsgLength());
//		log.info("传输原因：" + Detail104.getTransferReason());
//		log.info("终端地址：" + Detail104.getTerminalAddress());
//		log.info("消息地址：" + Detail104.getMessageAddress());
//		log.info("消息结构：" + Detail104.getMessages());
//		log.info("是否有消息元素：" + Detail104.isMessage());
//		log.info("判断是否有限定词：" + Detail104.getQualifiersType());
//		log.info("判断是否有时标：" + Detail104.isTimeScaleExit());
//		log.info("判断消息是否连续：" + Detail104.isContinuous());
//		if(Detail104.getMsgLength()>0){
//			for (int i = 0; i<Detail104.getMsgLength();i++) {
//				log.info(String.valueOf(Detail104.getMessages().get(i)));
//			}
//		}
//		try {
//			log.info("是否有消息元素：" + Detail104.getQualifiersType().getValue());
//		}catch (Exception e){}
//
//		log.info("限定词：" + Detail104.getQualifiersType().getValue());
//		log.info("时标：" + Detail104.getTimeScale());
//		log.info("限定词：" + Detail104.getHexString());
//
//		System.out.println(Detail104);
//		System.err.print("收到消息");
    }

}
