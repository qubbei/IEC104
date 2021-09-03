package com.iot.protocol.iec104.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 默认的配置
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Deprecated
public class DefaultIec104Config extends Iec104Config {

    public DefaultIec104Config() {
        setFrameAmountMax((short) 1);
        setTerminalAddress((short) 1);
    }
}
