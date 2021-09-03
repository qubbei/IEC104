package com.iot.protocol.iec104.core;

import com.iot.protocol.iec104.common.BasicInstruction104;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 定时任务管理池--Client端使用
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
@Slf4j
public class ScheduledTaskPool {

    /**
     * 发送指令
     */
    private ChannelHandlerContext ctx;
    /**
     * 发送启动指令的线程
     */
    private Thread sendStartThread;
    /**
     * 循环发送启动指令线程的状态
     */
    private Boolean sendStartStatus = false;
    /**
     * 发送测试指令的线程 类似心跳
     */
    private Thread sendTestThread;
    /**
     * 发送测试指令线程的状态
     */
    private Boolean sendTestStatus = false;
    /**
     * 发送总召唤指令状态
     */
    private Boolean senGeneralCallStatus = false;
    /**
     * 启动指令收到确认后固定时间内发送总召唤指令
     */
    private Thread generalCallTThread;

    public ScheduledTaskPool(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * 发送启动帧
     */
    public void sendStartFrame() {
        synchronized (sendStartStatus) {
            if (sendStartThread != null) {
                sendStartStatus = true;
                sendStartThread.start();
            } else if (sendStartThread == null) {
                sendStartStatus = true;
                sendStartThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (sendStartStatus) {
                            try {
                                ctx.channel().writeAndFlush(BasicInstruction104.START);
                                log.info("发送启动链路指令");
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                sendStartThread.start();
            }
        }
    }

    /**
     * 停止发送确认帧
     */
    public void stopSendStartFrame() {
        if (sendStartThread != null) {
            sendStartStatus = false;
        }
    }


    /**
     * 发送测试帧
     */
    public void sendTestFrame() {
        synchronized (sendTestStatus) {
            if (sendTestThread != null && sendTestThread.getState() == Thread.State.TERMINATED) {
                sendTestStatus = true;
                sendTestThread.start();
            } else if (sendTestThread == null) {
                sendTestStatus = true;
                sendTestThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (sendTestStatus) {
                            try {
                                log.info("发送测试链路指令");
                                ctx.channel().writeAndFlush(BasicInstruction104.TEST);
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                sendTestThread.start();
            }
        }
    }

    /**
     * 停止发送测试帧
     */
    public void stopSendTestFrame() {
        if (sendTestThread != null) {
            sendTestStatus = false;
        }
    }

    /**
     * 发送总召唤
     */
    public void sendGeneralCall() {
        synchronized (senGeneralCallStatus) {
            if (generalCallTThread != null && generalCallTThread.getState() == Thread.State.TERMINATED) {
                senGeneralCallStatus = true;
                generalCallTThread.start();
            } else if (generalCallTThread == null) {
                senGeneralCallStatus = true;
                generalCallTThread = new Thread(() -> {
                    while (sendTestStatus) {
                        try {
                            log.info("发送总召唤指令");
                            ctx.channel().writeAndFlush(BasicInstruction104.getGeneralCallDetail104());
                            Thread.sleep(1000 * 60 * 3);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                generalCallTThread.start();
            }
        }
    }

    /**
     * 发送电度召唤
     */
    public void sendElectricityCall() throws InterruptedException {
        log.info("发送电度召唤指令");
        ctx.channel().writeAndFlush(BasicInstruction104.getElectricityCallDetail104());
        Thread.sleep(1000 * 60 * 3);
    }

}
