package com.iot.protocol.iec104.server.master;

import com.iot.protocol.iec104.common.BasicInstruction104;
import com.iot.protocol.iec104.core.CachedThreadPool;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.server.handler.ChannelHandler;
import com.iot.protocol.iec104.server.handler.DataHandler;
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
    public void channelRead(ChannelHandler ctx, MessageDetail detail104) throws Exception {
        log.info("收到消息: {}", detail104.toString());
//        log.info("启动字符：" + detail104.getHeader());
//        log.info("字节长度：" + detail104.getApduLength());
//        byte[] control = detail104.getControl();
//        log.info("控制域：Accept->{}, Send->{}", Iec104Util.getAccept(control), Iec104Util.getSend(control));
//        log.info("类型标识：" + detail104.getTypeIdentifier().getValue());
//        log.info("可变结构限定词：" + detail104.isContinuous());
//        log.info("传输原因：" + detail104.getTransferReason());
//        log.info("终端地址：" + detail104.getTerminalAddress());
//        log.info("消息地址：" + detail104.getMessageAddress());
//        log.info("数据个数：" + detail104.getMessages().size());
//        log.info("消息结构：" + detail104.getMessages());
//        log.info("是否有消息元素：" + detail104.isMessage());
//        log.info("判断是否有限定词：" + detail104.getQualifiersType());
//        log.info("判断是否有时标：" + detail104.isTimeScaleExit());
//        log.info("判断消息是否连续：" + detail104.isContinuous());
//        try {
//            log.info("是否有消息元素：" + detail104.getQualifiersType().getValue());
//        } catch (Exception ignored) {
//        }
//        log.info("限定词：" + detail104.getQualifiersType().getValue());
//        log.info("时标：" + detail104.getTimeScale());
//        log.info("限定词：" + detail104.getHexString());
    }
}
