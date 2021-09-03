package com.iot.protocol.iec104.core;

import com.iot.protocol.iec104.enums.TypeIdentifierEnum;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.message.MessageInfo;
import com.iot.protocol.util.ByteUtil;
import com.iot.protocol.util.Iec104Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 编码
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Encoder104 {


    public static byte[] encoder(MessageDetail detail104) throws IOException {
        Iec104Util.setMsgAttribute(detail104);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(detail104.getHeader());
        byte[] apduBytes = getApduBytes(detail104);
        int messageLen = apduBytes.length;
        bytes.write((byte) messageLen);
        bytes.write(apduBytes);
        return bytes.toByteArray();
    }

    /**
     * 将报文构造体对象 转化为byte
     *
     * @param detail104 报文构造体对象
     * @return {@link Byte}
     */
    private static byte[] getApduBytes(MessageDetail detail104) throws IOException {
        ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
        bOutput.write(detail104.getControl());
        if (detail104.getTypeIdentifier() == null) {
            // U帧或者S帧 后面没有字节 直接返回
            return bOutput.toByteArray();
        }
        bOutput.write(detail104.getTypeIdentifier().getValue());
        bOutput.write(Iec104Util.getChangedQualifier(detail104));
        bOutput.write(ByteUtil.shortToByteArray(detail104.getTransferReason()));
        bOutput.write((Iec104Util.getTerminalAddressByte(detail104.getTerminalAddress())));
        boolean flagQualifier = detail104.isQualifier() && TypeIdentifierEnum.isTelemetry(detail104.getTypeIdentifier());
        //  如果是是连续的则数据地址 只需要在开头写以后的数据单元就不需要再写了
        if (detail104.isContinuous()) {
            bOutput.write(Iec104Util.intToMessageAddress(detail104.getMessageAddress()));
            for (MessageInfo msgInfo : detail104.getMessages()) {
                bOutput.write(msgInfo.getMessageInfos());
                if (flagQualifier) {
                    bOutput.write(detail104.getQualifiersType().getValue());
                }
            }
        } else {
            for (MessageInfo msgInfo : detail104.getMessages()) {
                bOutput.write(Iec104Util.intToMessageAddress(msgInfo.getMessageAddress()));
                if (detail104.isMessage()) {
                    bOutput.write(msgInfo.getMessageInfos());
                }
                if (flagQualifier) {
                    bOutput.write(detail104.getQualifiersType().getValue());
                }
            }
        }
        if (detail104.isQualifier() && !TypeIdentifierEnum.isTelemetry(detail104.getTypeIdentifier())) {
            bOutput.write(detail104.getQualifiersType().getValue());
        }
        if (detail104.isTimeScaleExit()) {
            bOutput.write(ByteUtil.date2Hbyte(detail104.getTimeScale()));
        }
        return bOutput.toByteArray();
    }
}
