package com.iot.protocol.iec104.config;

import lombok.Data;

/**
 * 104规约的配置
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@Data
public class Iec104Config {

    /**
     * 接收到帧的数量到该值就要发一个确认帧
     */
    private short frameAmountMax = 1;

    /**
     * 终端地址
     */
    private short terminalAddress = 1;
}
