package com.iot.protocol.iec104.enums;

import lombok.Getter;

/**
 * 类型标识
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public enum TypeIdentifierEnum {

    /**
     * 单点遥信
     */
    onePointTeleIndication(0x01, 1),
    /**
     * 单点遥信带时标
     */
    onePointTimeTeleIndication(0x1E, 1),

    /**
     * 双点遥信
     */
    twoPointTeleIndication(0x03, 1),

    /**
     * 双点遥信带时标
     */
    twoPointTimeTeleIndication(0x1F, 1),

    /**
     * 测量值 归一化值 遥测
     */
    normalizedTelemetry(0x09, 2),
    /**
     * 测量值  标度化值 遥测
     */
    scaledTelemetry(0x0B, 2),
    /**
     * 测量值 短浮点数 遥测
     */
    shortFloatingPointTelemetry(0x0D, 2),

    /**
     * 单命令 遥控
     */
    onePointTeleControl(0x2D, 1),
    /**
     * 双命令遥控
     */
    twoPointTeleControl(0x2E, 1),

    /**
     * 读单个参数
     */
    readOneParameter(0x66, 4),
    /**
     * 读多个参数
     */
    readMultipleParameter(0x84, 4),

    /**
     * 预置单个参数命令(归一化值)
     */
    prefabActivationNormalizedOneParameter(0x30, 2),
    /**
     * 预置单个参数命令(标度化值)
     */
    prefabActivationScaledOneParameter(0x30, 2),
    /**
     * 预置单个参数命令(浮点数值)
     */
    prefabActivationFloatOneParameter(0x30, 4),
    /**
     * 预置多个参数(归一化值)
     */
    prefabActivationNormalizedMultipleParameter(0x88, 2),
    /**
     * 预置多个参数(标度化值)
     */
    prefabActivationScaledMultipleParameter(0x89, 2),
    /**
     * 预置多个参数(浮点数值)
     */
    prefabActivationFloatMultipleParameter(0x8A, 4),

    /**
     * 初始化结束
     */
    initEnd(0x46, 0),
    /**
     * 召唤命令
     */
    generalCall(0x64, 0),

    /**
     * 电度召唤
     */
    electricityCall(0x65, 0),
    /**
     * 电度数据
     */
    electricityCall_DATA(0x0F, 4),

    /**
     * 时钟同步
     */
    timeSynchronization(0x67, 0),
    /**
     * 复位进程
     */
    resetProcess(0x69, 0);

    @Getter
    private final byte value;
    @Getter
    private final int messageLength;

    TypeIdentifierEnum(int value, int messageLength) {
        this.value = (byte) value;
        this.messageLength = messageLength;
    }

    /**
     * 根据类型标识hex获取类型枚举
     *
     * @param value 类型标识对应的hex
     * @return {@link TypeIdentifierEnum}
     */
    public static TypeIdentifierEnum getTypeIdentifierEnum(byte value) {
        for (TypeIdentifierEnum type : TypeIdentifierEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }


    /**
     * 判断是否是遥测
     *
     * @param typeIdentifierEnum 类型标识
     * @return true-是 false-否
     */
    public static boolean isTelemetry(TypeIdentifierEnum typeIdentifierEnum) {
        return TypeIdentifierEnum.normalizedTelemetry == typeIdentifierEnum
                || shortFloatingPointTelemetry == typeIdentifierEnum;
    }

    /**
     * 判断是否是遥脉（电度数据）
     *
     * @param typeIdentifierEnum 类型标识
     * @return true-是 false-否
     */
    public static boolean isTelePulse(TypeIdentifierEnum typeIdentifierEnum) {
        return TypeIdentifierEnum.electricityCall_DATA == typeIdentifierEnum;
    }
}
