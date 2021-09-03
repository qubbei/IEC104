package com.iot.protocol.iec104.server;

import com.iot.protocol.iec104.config.Iec104Config;
import com.iot.protocol.iec104.server.handler.DataHandler;

/**
 * 主站抽象类
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public interface Iec104Master {

    /**
     * 服务启动方法
     *
     * @throws Exception
     */
    void run() throws Exception;

    /**
     * @param dataHandler
     * @Title: setDataHandler
     * 设置数据处理类
     */
    Iec104Master setDataHandler(DataHandler dataHandler);

    /**
     * 设置配置文件
     *
     * @param iec104Confiig
     * @return
     */
    Iec104Master setConfig(Iec104Config iec104Confiig);

}
