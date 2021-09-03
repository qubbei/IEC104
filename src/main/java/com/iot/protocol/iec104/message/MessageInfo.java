package com.iot.protocol.iec104.message;

import com.iot.protocol.iec104.enums.QualifiersEnum;
import lombok.Data;

/**
 * 报文中 的消息部分
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@Data
public class MessageInfo {
    /**
     * 消息地址 字节  16385
     */
    private int messageAddress;

    /**
     * 信息元素集合 1 2 4 个字节
     */
    private byte[] messageInfos;

    /**
     * 限定词
     */
    private QualifiersEnum qualifiersType;
//	/**
//	 *
//	 * 时标
//	 */
//	private  Date timeScale;

    /**
     * 消息详情
     */
    private int messageInfoLength;

}
