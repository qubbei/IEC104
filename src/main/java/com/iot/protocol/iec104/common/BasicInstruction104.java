package com.iot.protocol.iec104.common;

import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.message.MessageInfo;
import com.iot.protocol.iec104.enums.QualifiersEnum;
import com.iot.protocol.iec104.enums.TypeIdentifierEnum;
import com.iot.protocol.iec104.enums.UControlEnum;
import com.iot.protocol.util.ByteUtil;
import com.iot.protocol.util.Iec104Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 104 规约的基本固定指令封装
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class BasicInstruction104 {

    /**
     * 链路启动指令
     */
    public static final byte[] START = new byte[]{0x68, 0x04, 0x07, 0x00, 0x00, 0x00};
    /**
     * 启动确认指令
     */
    public static final byte[] START_REPLY = new byte[]{0x68, 0x04, 0x0B, 0x00, 0x00, 0x00};

    /**
     * 测试指令
     */
    public static final byte[] TEST = new byte[]{0x68, 0x04, 0x43, 0x00, 0x00, 0x00};
    /**
     * 测试确认指令
     */
    public static final byte[] TEST_REPLY = new byte[]{0x68, 0x04, (byte) 0x83, 0x00, 0x00, 0x00};

    /**
     * 停止确认指令
     */
    public static final byte[] STOP_REPLY = new byte[]{0x68, 0x04, 0x23, 0x00, 0x00, 0x00};


    /**
     * 总召唤指令构造对象
     *
     * @return {@link MessageDetail}
     */
    public static MessageDetail getGeneralCallDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.generalCall;
        int sq = 0;
        boolean isContinuous = sq == 0 ? false : true;
        // 接收序号
        short accept = 0, send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
        short transferReason = 6;
        boolean isTest = false;
        boolean isPn = true;
        // 终端地址 实际发生的时候会被替换
        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;
        QualifiersEnum qualifiers = QualifiersEnum.generalCallGroupingQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
        return new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPn, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
    }


    /**
     * 总召唤确认指令
     *
     * @return {@link MessageDetail}
     */
    public static MessageDetail getGeneralCallReplyDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.generalCall;
        //SQ=0 length =1
        int sq = 0;
        boolean isContinuous = sq != 0;
        // 接收序号,发送序号
        short accept = 0, send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        short transferReason = 7;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;
        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;
        QualifiersEnum qualifiers = QualifiersEnum.generalCallGroupingQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
        return new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
    }


    /**
     * 总召唤结束指令
     *
     * @return {@link MessageDetail}
     */
    public static MessageDetail getGeneralCallEndDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.generalCall;
        //SQ=0 length =1
        int sq = 0;
        boolean isContinuous = sq == 0 ? false : true;
        // 接收序号
        short accept = 1, send = 4;
        byte[] control = Iec104Util.getIControl(accept, send);
        short transferReason = 0x0A;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;

        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;
        QualifiersEnum qualifiers = QualifiersEnum.generalCallGroupingQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
//		MessageInfo message = new MessageInfo();
//		message.setQualifiersType(qualifiers);
//		message.setMessageInfos(new byte[] {});
//
//		messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
        return Detail104;
    }

    /**
     * 链路启动初始化指令
     *
     * @return {@link MessageDetail}
     */
    public static MessageDetail getInitDetail104() {
        byte[] control = ByteUtil.intToByteArray(UControlEnum.START.getValue());
        MessageDetail Detail104 = new MessageDetail(control);
        return Detail104;
    }

    /**
     * 电度召唤指令
     */
    public static MessageDetail getElectricityCallDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.electricityCall;
        int sq = 0;
        boolean isContinuous = sq != 0;
        // 接收序号,发送序号
        short accept = 0, send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        short transferReason = 6;
        boolean isTest = false;
        boolean isPn = true;
        // 终端地址 实际发生的时候会被替换
        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;
        QualifiersEnum qualifiers = QualifiersEnum.electricityCallQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
        MessageInfo message = new MessageInfo();
        message.setMessageInfos(new byte[]{});
        messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPn, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
        return Detail104;
    }
}
