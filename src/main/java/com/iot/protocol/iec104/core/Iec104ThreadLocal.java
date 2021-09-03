package com.iot.protocol.iec104.core;


import com.iot.protocol.iec104.config.Iec104Config;

/**
 * 线程变量管理
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class Iec104ThreadLocal {

    /**
     * 定时发送启动链指令 测试链指令
     */
    private static ThreadLocal<ScheduledTaskPool> scheduledTaskPoolThreadLocal = new ThreadLocal<>();

    /**
     * 返回 发送序号 和接收序号  定时发送S帧
     */
    private static ThreadLocal<ControlManageUtil> controlPoolThreadLocal = new ThreadLocal<>();

    /**
     * 存放相关配置文件
     */
    private static ThreadLocal<Iec104Config> iec104ConfigThreadLocal = new ThreadLocal<>();


    public static void setScheduledTaskPool(ScheduledTaskPool scheduledTaskPool) {
        scheduledTaskPoolThreadLocal.set(scheduledTaskPool);
    }

    public static ScheduledTaskPool getScheduledTaskPool() {
        return scheduledTaskPoolThreadLocal.get();
    }

    public static void setControlPool(ControlManageUtil controlPool) {
        controlPoolThreadLocal.set(controlPool);
    }

    public static ControlManageUtil getControlPool() {
        return controlPoolThreadLocal.get();
    }


    public static Iec104Config getIec104Config() {
        return iec104ConfigThreadLocal.get();
    }


    public static void setIec104Config(Iec104Config iec104Config) {
        iec104ConfigThreadLocal.set(iec104Config);
    }


}
