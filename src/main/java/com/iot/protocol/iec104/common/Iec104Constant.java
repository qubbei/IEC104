package com.iot.protocol.iec104.common;

/**
 * 常量
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Iec104Constant {

    /**
     * 固定头
     */
    public static final byte HEAD_DATA = 0x68;

    /**
     * 控制域长度
     */
    public static final byte CONTROL_LENGTH = 0x04;

    /**
     * APCI 长度（最小报文-系统报文）
     */
    public static final byte APCI_LENGTH = 0x06;

    /**
     * 控制域中 接收序号低位坐标
     */
    public static final int ACCEPT_LOW_INDEX = 2;

    /**
     * 控制域中 接收序号高位坐标
     */
    public static final int ACCEPT_HIGH_INDEX = 3;

    /**
     * 控制域中 最大发送序号
     */
    public static final Short SEND_MAX = 32767;

    /**
     * 控制域中 最小发送序号
     */
    public static final Short SEND_MIN = 0;
}
