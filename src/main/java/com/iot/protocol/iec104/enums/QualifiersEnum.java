package com.iot.protocol.iec104.enums;

import lombok.Getter;

/**
 * 限定词
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public enum QualifiersEnum {

    /**
     * 总召唤限定词
     */
    generalCallQualifiers(TypeIdentifierEnum.generalCall, 0x20),

    /**
     * 总召唤限定词 支持 老版的分组
     */
    generalCallGroupingQualifiers(TypeIdentifierEnum.generalCall, 0x14),

    /**
     * 电度召唤限定词
     */
    electricityCallQualifiers(TypeIdentifierEnum.electricityCall, 0x05),
    /**
     * 电度召唤应答限定词
     */
    electricityCallReplyQualifiers(TypeIdentifierEnum.electricityCall_DATA, 0x00),

    /**
     * 复位进程限定词
     */
    resetPprocessQualifiers(TypeIdentifierEnum.resetProcess, 0x01),
    /**
     * 初始化原因 当地电源合上
     */
    localCloseUpQualifiers(TypeIdentifierEnum.initEnd, 0x00),
    /**
     * 初始化原因 当地手动复位
     */
    localMmanualResetQualifiers(TypeIdentifierEnum.initEnd, 0x01),
    /**
     * 远方复位
     */
    distanceResetQualifiers(TypeIdentifierEnum.initEnd, 0x02),
    /**
     * 品质描述词  遥测
     */
    qualityQualifiers(null, 0x00),
    /**
     * 设置命令限定词  选择预置参数 1000 0000
     */
    prefabParameterQualifiers(null, 0x80),
    /**
     * 设置命令限定词  执行激活参数
     */
    activationParameterQualifiers(null, 0x00);

    @Getter
    private byte value;
    @Getter
    private TypeIdentifierEnum typeIdentifier;

    QualifiersEnum(TypeIdentifierEnum typeIdentifier, int value) {
        this.value = (byte) value;
        this.typeIdentifier = typeIdentifier;
    }


    /**
     * 根据传输类型和 限定词的关系返回 限定词的类型
     *
     * @param typeIdentifier
     * @param value
     * @return
     */
    public static QualifiersEnum getQualifiersEnum(TypeIdentifierEnum typeIdentifier, byte value) {
        for (QualifiersEnum type : QualifiersEnum.values()) {
            if (type.getValue() == value && type.getTypeIdentifier() == typeIdentifier) {
                return type;
            }
        }
        // 品质描述词和设置参数 限定词对应多个值 所以需要做特殊处理
        QualifiersEnum qualifiersEnum = null;
        if ((TypeIdentifierEnum.normalizedTelemetry.equals(typeIdentifier)
                || TypeIdentifierEnum.scaledTelemetry.equals(typeIdentifier)
                || TypeIdentifierEnum.shortFloatingPointTelemetry.equals(typeIdentifier))
                && qualityQualifiers.getValue() == value) {
            qualifiersEnum = qualityQualifiers;
        }

        //设置命令限定词 （参数预置）
        if (TypeIdentifierEnum.readOneParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.readMultipleParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.prefabActivationNormalizedOneParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.prefabActivationScaledOneParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.prefabActivationFloatOneParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.prefabActivationNormalizedMultipleParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.prefabActivationScaledMultipleParameter.equals(typeIdentifier)
                || TypeIdentifierEnum.prefabActivationFloatMultipleParameter.equals(typeIdentifier)){
            if (prefabParameterQualifiers.getValue() == value) {
                qualifiersEnum = prefabParameterQualifiers;
            }else if(activationParameterQualifiers.getValue() == value){
                qualifiersEnum = activationParameterQualifiers;
            }
        }

        return qualifiersEnum;
    }
}
