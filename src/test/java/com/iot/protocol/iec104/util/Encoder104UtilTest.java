package com.iot.protocol.iec104.util;

import com.iot.protocol.iec104.core.Decoder104;
import com.iot.protocol.iec104.core.Encoder104;
import com.iot.protocol.iec104.enums.QualifiersEnum;
import com.iot.protocol.iec104.enums.TypeIdentifierEnum;
import com.iot.protocol.iec104.enums.UControlEnum;
import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.message.MessageInfo;
import com.iot.protocol.util.ByteUtil;
import com.iot.protocol.util.Iec104Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(Parameterized.class)
public class Encoder104UtilTest {

    private MessageDetail originalObj;
    private String name;

    public Encoder104UtilTest(Object originalObj, String name) {
        this.originalObj = (MessageDetail) originalObj;
        this.name = name;
    }

    @SuppressWarnings("rawtypes")
    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {getGeneralCallDetail104(), "总召唤指令"},
//			{getEndGeneralCallDetail104(), "结束总召唤指令"},
//			{getYesGeneralCallDetail104(), "总召唤确认指令"},
//			{getInitDetail104(), "初始化指令"},
//			{getInitYesDetail104(), "初始化确认指令"},
//			{getInitEndDetail104(), "初始化结束指令"},
//			{getSDetail104(), "S帧指令"},
//				{getPointInfo(), "点位指令"},
        });
    }

    @Test
    public void testEncoder() throws IOException {
        byte[] bytes = Encoder104.encoder(originalObj);
        log.info(name + ": " + ByteUtil.byteArrayToHexString(bytes));
        MessageDetail oldObj = Decoder104.decoder(bytes);
        judge(originalObj, oldObj);
//		assertEquals(name + " 长度和预计十六进制输出不匹配", Util.byteArrayToHexString(bytes).length(), originalObj.getHexString().length());
        assertEquals(name + "和预计十六进制输出不匹配", ByteUtil.byteArrayToHexString(bytes).toUpperCase(), originalObj.getHexString().toUpperCase());
    }


    /**
     * 类型标识: 召唤命令
     */
    private static MessageDetail getGeneralCallDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.generalCall;
        //SQ=0 length =1
        int sq = 1;
        boolean isContinuous = sq != 0;
        // 接收序号 发送序号
        short accept = 0, send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        short transferReason = 6;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;
        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;

        QualifiersEnum qualifiers = QualifiersEnum.generalCallQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
//		MessageInfo message = new MessageInfo();
//		// 不写
//		message.setQualifiersType(qualifiers);
//		message.setMessageInfos(new byte[] {});
//
//		messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
//
//		68 0E 0000 0000  64(类型标识) 01(可变结构限定词)0600(传输原因)0100(公共地址)0000(信息体地址)0020
//		68 0E 0000 0000  64         80             06000        1000        00000  20                 20
        Detail104.setHexString("680E0000000064800600010000000020");
        return Detail104;
    }


    /**
     * 【总召唤确认指令】构造对象
     */
    private static MessageDetail getYesGeneralCallDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.generalCall;
        //SQ=0 length =1
        int sq = 1;
        boolean isContinuous = sq == 0 ? false : true;
        // 接收序号
        short accept = 0;
        // 发送序号
        short send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
        short transferReason = 7;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;

        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;

        QualifiersEnum qualifiers = QualifiersEnum.generalCallQualifiers;
//		List<MessageInfo> messages = new ArrayList<>();
        MessageInfo message = new MessageInfo();
//		message.setQualifiersType(qualifiers);
//		message.setMessageInfos(new byte[] {});

//		messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, new ArrayList<>(), null, qualifiers);
//
//      68 0E 0000 0000  64          80             0700         0100        0000          0020
//		68 0E 0000 0000  64(类型标识) 01(可变结构限定词)0600(传输原因)0100(公共地址)0000(信息体地址)0020
        Detail104.setHexString("680E0000000064800700010000000020");
        return Detail104;
    }

    /**
     * 结束总召唤指令对象
     */
    private static MessageDetail getEndGeneralCallDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.generalCall;
        //SQ=0 length =1
        int sq = 0;
        boolean isContinuous = sq == 0 ? false : true;
        // 接收序号
        short accept = 1;
        // 发送序号
        short send = 4;
//		int control = 0x08000200;

        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
        short transferReason = 0x0A;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;

        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;

        QualifiersEnum qualifiers = QualifiersEnum.generalCallQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
        MessageInfo message = new MessageInfo();
//		message.setQualifiersType(qualifiers);
        message.setMessageInfos(new byte[]{});

        messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
//      68 0E 0800 0200 64 01 0A00 0100 000000 20
//		68 0E 0800 0200 64 01 0A00 0100 000000 20
        Detail104.setHexString("680E0800020064010A00010000000020");
        return Detail104;
    }

    /**
     * 【初始化指令】构造对象
     *
     * @return {@link MessageDetail}
     */
    private static MessageDetail getInitDetail104() {
        byte[] control = ByteUtil.intToByteArray(UControlEnum.START.getValue());
        MessageDetail Detail104 = new MessageDetail(control);
        //6804 07(START命令) 00 0000
        Detail104.setHexString("680407000000");
        return Detail104;
    }

    /**
     * 【初始化确认指令】构造对象
     *
     * @return {@link MessageDetail}
     */
    private static MessageDetail getInitYesDetail104() {
        byte[] control = ByteUtil.intToByteArray((UControlEnum.START_REPLY.getValue()));
        MessageDetail Detail104 = new MessageDetail(control);
        //6804 07(START命令) 00 0000
        Detail104.setHexString("68040B000000");
        return Detail104;
    }

    /**
     * 【初始化结束指令】构造对象
     *
     * @return {@link MessageDetail}
     */
    private static MessageDetail getInitEndDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.initEnd;
        //SQ=0 length =1
        int sq = 1;
        boolean isContinuous = sq == 0 ? false : true;
        // 接收序号
        short accept = 0;
        // 发送序号
        short send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
        short transferReason = 4;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;

        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 0;

        QualifiersEnum qualifiers = QualifiersEnum.localMmanualResetQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
        MessageInfo message = new MessageInfo();
//		message.setQualifiersType(qualifiers);
//		message.setMessageInfos(new byte[] {});
//
//		messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
//		68 0E 0000 0000  46          80          0400010000000001
//		68 0E 0000 0000  46          01             0400         0100        0000          0001
//		68 0E 0000 0000  64(类型标识) 01(可变结构限定词)0400(传输原因)0100(公共地址)0000(信息体地址)0020
        Detail104.setHexString("680E0000000046800400010000000001");
        return Detail104;
    }

    /**
     * 【S帧指令】构造对象
     *
     * @return {@link MessageDetail}
     */
    private static MessageDetail getSDetail104() {
        short accept = 1; // 01 00 02 00
//  	接收序号是1
//		byte[] control = ByteUtil.intToByteArray(0x1000200);
        byte[] control = Iec104Util.getSControl(accept);
        MessageDetail Detail104 = new MessageDetail(control);
        //6804 0100() 0200
        Detail104.setHexString("680401000200");
        return Detail104;
    }


    public void judge(MessageDetail originalObj, MessageDetail oldObj) {
        assertEquals(name + ": start :", originalObj.getHeader(), oldObj.getHeader());
        assertEquals(name + ": apduLength :", originalObj.getApduLength(), oldObj.getApduLength());

        int index = 0;
        while (index < originalObj.getControl().length) {
            assertEquals(name + ": control :", originalObj.getControl()[index], oldObj.getControl()[index++]);
        }
        assertEquals(name + ": typeIdentifier :", originalObj.getTypeIdentifier(), oldObj.getTypeIdentifier());
        assertEquals(name + ": isContinuous :", originalObj.isContinuous(), oldObj.isContinuous());
        assertEquals(name + ": msgSize :", originalObj.getMsgSize(), oldObj.getMsgSize());
        assertEquals(name + ": transferReason :", originalObj.getTransferReason(), oldObj.getTransferReason());
        assertEquals(name + ": terminalAddress :", originalObj.getTerminalAddress(), oldObj.getTerminalAddress());
        assertEquals(name + ": messageAddress :", originalObj.getMessageAddress(), oldObj.getMessageAddress());
        assertEquals(name + ": qualifiersType :", originalObj.getQualifiersType(), oldObj.getQualifiersType());
        assertEquals(name + ": timeScale :", originalObj.getTimeScale(), oldObj.getTimeScale());
        assertEquals(name + ": messageAddress :", originalObj.getMessageAddress(), oldObj.getMessageAddress());
        assertEquals(name + ": messageAddress :", originalObj.getMessageAddress(), oldObj.getMessageAddress());
        assertEquals(name + ": messageAddress :", originalObj.getMessageAddress(), oldObj.getMessageAddress());
        judgeMessages(originalObj, oldObj);
        System.err.println(name + " 指令测试结束");
    }

    private void judgeMessages(MessageDetail originalObj, MessageDetail oldObj) {
        assertEquals(name + ": messagesSize :", originalObj.getMessages().size(), oldObj.getMessages().size());
        int index = 0;
        while (index < originalObj.getMessages().size()) {
            MessageInfo originalMessagesObj = originalObj.getMessages().get(index);
            MessageInfo oldMessageObj = oldObj.getMessages().get(index);

//			assertEquals(name + ": Messages timeScale :", originalMessagesObj.getTimeScale(), oldMessageObj.getTimeScale());
            Assert.assertEquals(name + ": Messages qualifiersType :", originalMessagesObj.getQualifiersType(), oldMessageObj.getQualifiersType());
            assertEquals(name + ": Messages timeScale :", originalMessagesObj.getMessageInfos().length, oldMessageObj.getMessageInfos().length);
            int messageInfoIndex = 0;
            while (messageInfoIndex < originalMessagesObj.getMessageInfos().length) {
                assertEquals(name + ": Messages messageInfos :", originalMessagesObj.getMessageInfos()[messageInfoIndex], oldMessageObj.getMessageInfos()[messageInfoIndex]);
                messageInfoIndex++;
            }
            index++;
        }
    }

    /**
     * 【点位指令】构造对象
     *
     * @return {@link MessageDetail}
     */
    private static MessageDetail getPointInfo() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.onePointTeleControl;
        //SQ=0 length =1
        int sq = 0;
        boolean isContinuous = sq != 0;
        // 接收序号
        short accept = 0;
        // 发送序号
        short send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
        short transferReason = 6;
        // true-1   false- 0
        boolean isTest = false;
        // true-0   false-1
        boolean isPN = true;
        short terminalAddress = 1;
        // 消息地址 总召唤地址为0
        int messageAddress = 1;

        List<MessageInfo> messages = new ArrayList<>();
        MessageInfo message = new MessageInfo();
        message.setMessageInfos(new byte[]{0x00});
        message.setMessageAddress(messageAddress);
        messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, 0, messages, null, null);
        //6804 07(START命令) 00 0000


//		        68 0e 0000 0000  2d          01             0600        0100         0100          0000
        //		68 0E 0000 0000  2d(类型标识) 01(可变结构限定词)0600(传输原因)0100(公共地址)0000(信息体地址)0000
        Detail104.setHexString("680e000000002d010600010001000000");
        return Detail104;
    }


    public static MessageDetail customInfo() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.shortFloatingPointTelemetry;
        //SQ=0 length =1
        int sq = 1;
        boolean isContinuous = sq != 0;
        // 接收序号
        short accept = 1;
        // 发送序号
        short send = 4;
        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
        short transferReason = 0x01;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;
        short terminalAddress = 1;
        int messageAddress = 100;
        // 老板限定词
        QualifiersEnum qualifiers = QualifiersEnum.qualityQualifiers;
        List<MessageInfo> messages = new ArrayList<>();
        MessageInfo message = new MessageInfo();
        message.setMessageAddress(2);
        message.setMessageInfos(new byte[]{0x12, 0x34});
        messages.add(message);

        MessageInfo message2 = new MessageInfo();
        message.setMessageAddress(3);
        message2.setMessageInfos(new byte[]{0x56, 0x78});
        messages.add(message2);


        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPN, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
        return Detail104;
    }


    /**
     * 电度召唤指令
     *
     * @return {@link MessageDetail}
     */
    public static MessageDetail getElectricityCallDetail104() {
        TypeIdentifierEnum typeIdentifierEnum = TypeIdentifierEnum.electricityCall;
        int sq = 0;
        boolean isContinuous = sq == 0 ? false : true;
        // 接收序号
        short accept = 2;
        // 发送序号
        short send = 2;
        byte[] control = Iec104Util.getIControl(accept, send);
        // 传输原因
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
        //message.setQualifiersType(QualifiersEnum.electricityCallQualifiers_05);
        message.setMessageInfos(new byte[]{});
        messages.add(message);
        MessageDetail Detail104 = new MessageDetail(control, typeIdentifierEnum, isContinuous, isTest, isPn, transferReason,
                terminalAddress, messageAddress, messages, null, qualifiers);
        return Detail104;
        //68 0B 00 00 00 00 65 00 06 00 01 00 05
    }


}
