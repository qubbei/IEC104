package com.iot.protocol.iec104.message;

import com.iot.protocol.iec104.enums.QualifiersEnum;
import com.iot.protocol.iec104.enums.TypeIdentifierEnum;
import com.iot.protocol.util.Iec104Util;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 整条报文结构对象
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@Data
public class MessageDetail {

    /**
     * 启动字符 固定头 占1个字节
     */
    private byte header = 0x68;

    /**
     * APDU报文长度 占1个字节（剩余报文的总长度,最小4字节,最大为253字节）
     */
    private int apduLength = 4;

    /**
     * 控制域 占4个字节
     */
    private byte[] control;

    /**
     * 类型标识 占1字节
     */
    private TypeIdentifierEnum typeIdentifier;

    /**
     * 可变结构限定词 占1个字节<br>
     * true  SQ = 0  地址不连续 数目number 是 信息对象的数目<br>
     * false SQ = 1  地址连续   单个对象的信息元素或者信息元素的集合的数目<br>
     */
    private boolean isContinuous;

    /**
     * 传输原因 占2个字节
     */
    private short transferReason;

    /**
     * 终端地址 占2个字节 也就是应用服务数据单元公共地址
     */
    private short terminalAddress;

    /**
     * 消息地址 占3字节
     */
    private int messageAddress;

    /**
     * 消息结构 每个数据占4个字节
     */
    private List<MessageInfo> messages;

    /**
     * 数据体中的数据个数
     */
    private int msgSize;
    /**
     * 判断是否有消息元素
     */
    private boolean isMessage;
    /**
     * 判断是否有限定词
     */
    private boolean isQualifier;
    /**
     * 判断是否有时标
     */
    private boolean isTimeScaleExit;

    /**
     * 限定词
     */
    private QualifiersEnum qualifiersType;

    /**
     * 时标
     */
    private Date timeScale;

    /**
     * 十六进制 字符串
     */
    private String hexString;

    public MessageDetail() {
    }

    /**
     * @param control            控制域
     * @param typeIdentifierEnum 类型标识
     * @param sq                 0 地址不连续  1 地址连续
     * @param isTest             传输原因  0 未试验 1 试验
     * @param isPn               肯定确认 和否定确认
     * @param transferReason     传输原因 后六个比特位
     * @param terminalAddress    服务地址
     * @param messageAddress     消息地址
     * @param messages           消息列表
     * @param timeScale          时间
     * @param qualifiers         限定词
     */
    public MessageDetail(byte[] control, TypeIdentifierEnum typeIdentifierEnum, boolean sq,
                         boolean isTest, boolean isPn, short transferReason, short terminalAddress, int messageAddress,
                         List<MessageInfo> messages, Date timeScale, QualifiersEnum qualifiers) {
        this.control = control;
        this.typeIdentifier = typeIdentifierEnum;
        this.isContinuous = sq;
        this.msgSize = messages.size();
        this.transferReason = Iec104Util.getTransferReasonShort(isTest, isPn, transferReason);
        this.messages = messages;
        this.terminalAddress = terminalAddress;
        this.timeScale = timeScale;
        if (isContinuous) {
            this.messageAddress = messageAddress;
        }
        if (timeScale != null) {
            this.isTimeScaleExit = true;
        }
        this.qualifiersType = qualifiers;
    }


    /**
     * U 帧或者S帧
     *
     * @param control 控制域
     */
    public MessageDetail(byte[] control) {
        this.control = control;
        this.messages = new ArrayList<>();
    }

}
