package com.iot.protocol.iec104.enums;

import lombok.Getter;

/**
 * U帧 控制域固定指令
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public enum UControlEnum {

    /**
     * 启动命令
     */
    START(0x07000000),
    /**
     * 启动确认命令
     */
    START_REPLY(0x0B000000),

    /**
     * 测试命令
     */
    TEST(0x43000000),
    /**
     * 测试确认指令
     */
    TEST_REPLY(0x83000000),

    /**
     * 停止指令
     */
    STOP(0x13000000),
    /**
     * 停止确认
     */
    STOP_REPLY(0x23000000);

    @Getter
    private final int value;

    UControlEnum(int value) {
        this.value = value;
    }
}
