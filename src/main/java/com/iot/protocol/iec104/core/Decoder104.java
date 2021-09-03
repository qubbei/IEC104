package com.iot.protocol.iec104.core;

import com.iot.protocol.util.Iec104Util;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.message.MessageInfo;
import com.iot.protocol.iec104.enums.QualifiersEnum;
import com.iot.protocol.iec104.enums.TypeIdentifierEnum;
import com.iot.protocol.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 解码
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Decoder104 {

    /**
     * 将bytes转换成MessageDetail
     *
     * @param bytes 接收的hex报文
     * @return {@link MessageDetail}
     */
    public static MessageDetail decoder(byte[] bytes) {
        MessageDetail detail104 = new MessageDetail();
        int index = 0;
        detail104.setHeader(bytes[index++]);
        detail104.setApduLength(bytes[index++] & 0xFF);
        detail104.setControl(ByteUtil.getByte(bytes, index, 4));
        index += 4;
        if (detail104.getApduLength() <= 4) {
            return detail104;
        }
        detail104.setTypeIdentifier(TypeIdentifierEnum.getTypeIdentifierEnum(bytes[index++]));
        Iec104Util.setChangedQualifier(detail104, bytes[index++]);
        //Detail104.setTransferReason(ByteUtil.byteArrayToShort(ByteUtil.getByte(bytes, index, 2)));
        detail104.setTransferReason(ByteUtil.getByte(bytes, index, 2)[0]);
        index += 2;
        detail104.setTerminalAddress(Iec104Util.getTerminalAddressShort(ByteUtil.getByte(bytes, index, 2)));
        index += 2;
        Iec104Util.setMsgAttribute(detail104);
        setMessage(detail104, bytes, index);
        return detail104;
    }

    /**
     * 对消息进行编码
     *
     * @param detail104 报文结构体对象
     * @param bytes     接收到的hex
     * @param index     索引
     */
    public static void setMessage(MessageDetail detail104, byte[] bytes, int index) {
        if (detail104.isContinuous()) {
            setContinuesMessage(detail104, bytes, index);
        } else {
            setNoContinuesMessage(detail104, bytes, index);
        }
    }


    /**
     * 设置连续地址的消息，前三个字节是地址，只需要设置一个初始地址
     *
     * @param detail104 报文结构体对象
     * @param bytes     接收到的hex
     * @param index     索引
     */
    public static void setContinuesMessage(MessageDetail detail104, byte[] bytes, int index) {
        List<MessageInfo> messages = new ArrayList<>();
        int messageAddress = Iec104Util.messageAddressToInt(ByteUtil.getByte(bytes, index, 3));
        detail104.setMessageAddress(messageAddress);
        index += 3;
        TypeIdentifierEnum typeIdentifier = detail104.getTypeIdentifier();
        boolean isQualifier = detail104.isQualifier();
        boolean telemetry = TypeIdentifierEnum.isTelemetry(typeIdentifier);
        boolean telePulse = TypeIdentifierEnum.isTelePulse(typeIdentifier);
        if (detail104.isMessage()) {
            int messageLength = getMessageLength(detail104);
            int messageSize = 0;
            while (messageSize < detail104.getMsgSize()) {
                MessageInfo messageObj = new MessageInfo();
                messageObj.setMessageAddress(messageAddress);
                byte[] messageInfos = ByteUtil.getByte(bytes, index, messageLength);
                index += messageLength;
                messageObj.setMessageInfos(messageInfos);
                setMessageValue(detail104, messageObj);
                if (isQualifier && (telemetry || telePulse)) {
                    detail104.setQualifiersType(QualifiersEnum.getQualifiersEnum(typeIdentifier, bytes[index++]));
                }
                messageSize++;
                messageAddress++;
                messages.add(messageObj);
            }
        }
        if (isQualifier && (!telemetry || !telePulse)) {
            detail104.setQualifiersType(QualifiersEnum.getQualifiersEnum(typeIdentifier, bytes[index++]));
        }
        if (detail104.isTimeScaleExit()) {
            detail104.setTimeScale(ByteUtil.byte2Hdate(ByteUtil.getByte(bytes, index, 7)));
        }
        detail104.setMessages(messages);
    }


    /**
     * 设置不连续地址的消息
     *
     * @param detail104 报文结构体对象
     * @param bytes     接收到的hex
     * @param index     索引
     */
    public static void setNoContinuesMessage(MessageDetail detail104, byte[] bytes, int index) {
        List<MessageInfo> messages = new ArrayList<>();
        int messageLength = getMessageLength(detail104);
        int messageSize = 0;
        while (messageSize < detail104.getMsgSize()) {
            MessageInfo messageObj = new MessageInfo();
            messageObj.setMessageAddress(Iec104Util.messageAddressToInt(ByteUtil.getByte(bytes, index, 3)));
            index += 3;
            if (detail104.isMessage()) {
                byte[] messageInfos = ByteUtil.getByte(bytes, index, messageLength);
                index += messageLength;
                messageObj.setMessageInfos(messageInfos);
                setMessageValue(detail104, messageObj);
            } else {
                messageObj.setMessageInfos(new byte[]{});
            }
            if (detail104.isQualifier() && TypeIdentifierEnum.isTelemetry(detail104.getTypeIdentifier())) {
                detail104.setQualifiersType(QualifiersEnum.getQualifiersEnum(detail104.getTypeIdentifier(), bytes[index++]));
            }
            messageSize++;
            messages.add(messageObj);
        }
        if (detail104.isQualifier() && !TypeIdentifierEnum.isTelemetry(detail104.getTypeIdentifier())) {
            detail104.setQualifiersType(QualifiersEnum.getQualifiersEnum(detail104.getTypeIdentifier(), bytes[index++]));
        }
        if (detail104.isTimeScaleExit()) {
            detail104.setTimeScale(ByteUtil.byte2Hdate(ByteUtil.getByte(bytes, index, 7)));
        }
        detail104.setMessages(messages);
    }

    /**
     * 根据类型对数据的值进行解析
     */
    private static void setMessageValue(MessageDetail detail104, MessageInfo messageObj) {
        switch (detail104.getTypeIdentifier().getValue()) {
            case 0x09:
                // 遥测 测量值 归一化值 遥测
                break;
            case 0x0B:
                // 遥测 测量值  标度化值 遥测
                break;
            case 0x66:
                // 读单个参数
                break;
            case (byte) 0x84:
                //  读多个参数
                break;
            case 0x30:
                // 预置单个参数命令
                break;
            case (byte) 0x88:
                // 预置多个个参数
                break;
            default:
        }
    }

    /**
     * 根据类型标识返回消息长度
     */
    private static int getMessageLength(MessageDetail detail104) {
        return detail104.getTypeIdentifier().getMessageLength();
    }
}
