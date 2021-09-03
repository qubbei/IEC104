package com.iot.protocol.iec104.core;

import com.iot.protocol.iec104.common.Iec104Constant;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.util.Iec104Util;
import io.netty.channel.ChannelHandlerContext;

/**
 * 控制域的管理工具
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class ControlManageUtil {

    /**
     * 发送序号
     */
    private Short send;

    /**
     * 接收序号
     */
    private Short accept;

    /**
     * 接收到帧的数量
     */
    private Short frameAmount;

    /**
     * 发送S帧的锁
     */
    private Boolean sendSFrameLock;

    /**
     * 接收到帧的数量最大阀值
     */
    private short frameAmountMax;

    /**
     * 发送消息句柄
     */
    private ChannelHandlerContext ctx;


    public ControlManageUtil(ChannelHandlerContext ctx) {
        send = 0;
        accept = 0;
        frameAmount = 0;
        sendSFrameLock = true;
        frameAmountMax = 1;
        this.ctx = ctx;
    }

    /**
     * 启动S发送S确认帧 的任务
     */
    public void startSendFrameTask() {
        Runnable runnable = () -> {
            while (true) {
                try {
                    synchronized (sendSFrameLock) {
                        if (frameAmount >= frameAmountMax) {
                            // 查过最大帧 的数量就要发送一个确认帧出去
                            byte[] control = Iec104Util.getSControl(accept);
                            ctx.channel().writeAndFlush(Encoder104.encoder(new MessageDetail(control)));
                            frameAmount = 0;
                        }
                        sendSFrameLock.wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        CachedThreadPool.getCachedThreadPool().execute(runnable);
    }


    /**
     * 返回当前的发送序号
     */
    public short getSend() {
        synchronized (send) {
            short send = this.send;
            this.send++;
            if (send > Iec104Constant.SEND_MAX) {
                send = Iec104Constant.SEND_MIN;
            }
            return send;
        }
    }

    /**
     * 获取接收序号
     */
    public short getAccept() {
        return accept;
    }

    /**
     * 设置接收序号
     *
     * @param lastAccept 当前包接收序号
     */
    public void setAccept(short lastAccept) {
        synchronized (sendSFrameLock) {
            this.accept = lastAccept;
            frameAmount++;
            if (frameAmount >= frameAmountMax) {
                this.accept = lastAccept;
                sendSFrameLock.notifyAll();
            }
        }
    }

    /**
     * 设置阈值
     *
     * @param frameAmountMax 收到该阈值数量帧，发送一个确认帧
     */
    public ControlManageUtil setFrameAmountMax(short frameAmountMax) {
        this.frameAmountMax = frameAmountMax;
        return this;
    }
}
