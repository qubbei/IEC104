package com.iot.protocol.util;

import com.iot.protocol.iec104.enums.TypeIdentifierEnum;
import com.iot.protocol.iec104.enums.UControlEnum;
import com.iot.protocol.iec104.message.MessageDetail;

import static com.iot.protocol.iec104.common.Iec104Constant.CONTROL_LENGTH;

/**
 * Iec104协议工具类
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Iec104Util {

    /**
     * I帧格式 低位在前
     *
     * @param accept 接收序号
     * @param send   发送序号
     * @return {@link Byte}
     */
    public static byte[] getIControl(short accept, short send) {
        byte[] control = new byte[4];
        send = (short) (send << 1);
        control[0] = (byte) ((send));
        control[1] = (byte) ((send >> 8));
        accept = (short) (accept << 1);
        control[2] = (byte) ((accept));
        control[3] = (byte) ((accept >> 8));
        return control;
    }

    public static void main(String[] args) {
        System.out.println(1 << 1);
    }

    /**
     * 获取S帧控制域报文格式-byte数组
     *
     * @param accept 接收序号
     * @return {@link Byte}
     */
    public static byte[] getSControl(short accept) {
        byte[] control = new byte[4];
        short send = 1;
        control[0] = (byte) ((send));
        control[1] = (byte) ((0));
        accept = (short) (accept << 1);
        control[2] = (byte) ((accept));
        control[3] = (byte) ((accept >> 8));
        return control;
    }

    /**
     * 返回控制域中的接收序号
     *
     * @param control 控制域报文
     * @return 接收序号
     */
    public static short getAccept(byte[] control) {
        int accept = 0;
        short acceptLow = (short) (control[2] & 0xff);
        short acceptHigh = (short) (control[3] & 0xff);
        accept += acceptLow;
        accept += acceptHigh << 8;
        accept = accept >> 1;
        return (short) accept;

    }

    /**
     * 返回控制域中的发送序号
     *
     * @param control 控制域报文
     * @return 发送序号
     */
    public static short getSend(byte[] control) {
        int send = 0;
        short acceptLow = (short) (control[0] & 0xff);
        short acceptHigh = (short) (control[1] & 0xff);
        send += acceptLow;
        send += acceptHigh << 8;
        send = send >> 1;
        return (short) send;
    }

    /**
     * 返回U帧的类型
     *
     * @param control 控制域报文
     * @return {@link UControlEnum} 根据控制域报文 返回类型
     */
    public static UControlEnum getUcontrol(byte[] control) {
        if (control.length < CONTROL_LENGTH || control[1] != 0 || control[3] != 0 || control[2] != 0) {
            return null;
        }
        int controlInt = ByteUtil.byteArrayToInt(control);
        for (UControlEnum ucontrolEnum : UControlEnum.values()) {
            if (ucontrolEnum.getValue() == controlInt) {
                return ucontrolEnum;
            }
        }
        return null;
    }


    /**
     * 返回消息地址 其中低位在前
     *
     * @param i 十进制
     * @return {@link Byte}
     */
    public static byte[] intToMessageAddress(int i) {
        byte[] result = new byte[3];
        result[0] = (byte) (i & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        return result;
    }


    /**
     * 消息地址 只有三个
     *
     * @param bytes 消息体报文
     * @return 十进制的地址
     */
    public static int messageAddressToInt(byte[] bytes) {
        int value = 0;
        for (int i = 2; i >= 0; i--) {
            int shift = (2 - i) * 8;
            value += (bytes[2 - i] & 0xFF) << shift;
        }
        return value;
    }

    /**
     * 设置可变结构限定词
     *
     * @param detail104 MessageDetail对象
     * @param data      可变结构限定词对应的byte
     */
    public static void setChangedQualifier(MessageDetail detail104, byte data) {
        // 第一位是 0 地址不连续
        detail104.setContinuous((data & 0x80) != 0);
        // 先将第一位数置零 然后转换成int
        detail104.setMsgSize(data & (byte) 0x7F);
    }

    /**
     * 返回可变限定词byte数组
     *
     * @param detail104 消息构造体对象
     * @return {@link Byte}
     */
    public static byte getChangedQualifier(MessageDetail detail104) {
        byte changedQualifiers = (byte) detail104.getMsgSize();
        // 判断SQ  isContinuous false SQ = 0;否则 SQ =1 ,  同时将SQ设置在 可变限定词的D7位置
        int sq = detail104.isContinuous() ? 0x80 : 0;
        changedQualifiers = (byte) (sq | changedQualifiers);
        return changedQualifiers;
    }

    /**
     * 设置报文是否有消息元素、限定词、时标
     *
     * @param detail104 需要设置的对象
     */
    public static void setMsgAttribute(MessageDetail detail104) {
        boolean isMessage = !(TypeIdentifierEnum.generalCall.equals(detail104.getTypeIdentifier())  //总召唤无此项
                || TypeIdentifierEnum.timeSynchronization.equals(detail104.getTypeIdentifier()) // 时钟同步
                || TypeIdentifierEnum.resetProcess.equals(detail104.getTypeIdentifier()) // 复位进程
                || TypeIdentifierEnum.initEnd.equals(detail104.getTypeIdentifier()));
        detail104.setMessage(isMessage);

        boolean isQualifiers = !(TypeIdentifierEnum.timeSynchronization.equals(detail104.getTypeIdentifier())  // 时钟同步
                || TypeIdentifierEnum.onePointTeleIndication.equals(detail104.getTypeIdentifier()) //单点摇信
                || TypeIdentifierEnum.twoPointTeleIndication.equals(detail104.getTypeIdentifier()) // 双点摇信
                || TypeIdentifierEnum.onePointTeleControl.equals(detail104.getTypeIdentifier()) // 单命令遥控
                || TypeIdentifierEnum.twoPointTeleControl.equals(detail104.getTypeIdentifier())); // 双命令遥控
        detail104.setQualifier(isQualifiers);
        boolean isTimeScale = TypeIdentifierEnum.timeSynchronization.equals(detail104.getTypeIdentifier())  // 时钟同步
                || TypeIdentifierEnum.onePointTimeTeleIndication.equals(detail104.getTypeIdentifier()) // 摇信带时标 单点
                || TypeIdentifierEnum.twoPointTimeTeleIndication.equals(detail104.getTypeIdentifier()); //摇信带时标 双点
        detail104.setTimeScaleExit(isTimeScale);
    }

    /**
     * short 转换成两个 字节后是163  00    也就是  value[1] 中才有值
     * test 在D7位置 因此 值应该和  01000000 做与运算
     * P/N 0肯定确认  1否定确认
     *
     * @return 肯定或否定确认
     */
    public static boolean isYes(byte[] values) {
        return (values[0] & 1 << 6) == 0;
    }

    /**
     * short 转换成两个 字节后是163  00     也就是  value[1] 中才有值
     * test 在D7位置 因此 值应该和 10000000 做与运算
     * test 0 为试验  1 试验
     *
     * @return 是否试验
     */
    public static boolean isTest(byte[] values) {
        return (values[0] & 1 << 7) != 0;
    }

    /**
     * 返回具体的传输原因
     *
     * @param values 传输原因报文
     * @return 转化后的十进制
     */
    public static short getTransferReasonShort(byte[] values) {
        byte transferReason = values[0];
        // 前两位置零
        transferReason = (byte) (transferReason & 0x3E);
        return transferReason;
    }


    public static short getTransferReasonShort(boolean isTest, boolean isYes, short transferReason) {
        int t = isTest ? 1 : 0;
        int y = isYes ? 0 : 1;
        int transferReasonInt = t << 7 | transferReason;
        transferReasonInt = y << 6 | transferReasonInt;
        return (short) (transferReasonInt << 8);
    }


    /**
     * 返回终端地址对应的byte数组 其中低位在前
     *
     * @param terminalAddress 十进制的终端地址
     * @return {@link Byte} 十进制对应的byte数组
     */
    public static byte[] getTerminalAddressByte(short terminalAddress) {
        byte[] b = new byte[2];
        b[1] = (byte) ((terminalAddress >> 8) & 0xff);
        b[0] = (byte) (terminalAddress & 0xff);
        return b;
    }


    /**
     * 返回终端地址对应的十进制数值 其中低位在前
     *
     * @param terminalAddress byte数组类型的终端地址
     * @return 十进制的终端地址
     */
    public static short getTerminalAddressShort(byte[] terminalAddress) {
        short value = 0;
        value += (terminalAddress[0] & 0xFF);
        value += (terminalAddress[1] & 0xFF) << 8;
        return value;
    }
}
