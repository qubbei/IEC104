package com.iot.protocol.iec104.server;

import com.iot.protocol.iec104.config.Iec104Config;
import com.iot.protocol.iec104.server.handler.DataHandler;

/**
 * 从站抽象类
 * @author Mr.Qu
 * @since 2020/5/19
 */
public interface Iec104Slave {
	/**
	 * 
	* @Title: run
	*  启动主机
	* @throws Exception
	 */
	void run() throws Exception;
	
	
	/**
	 * 
	* @Title: setDataHandler
	*  设置数据处理类
	* @param dataHandler
	 */
	Iec104Slave setDataHandler(DataHandler dataHandler);


	/**
	 * 设置配置文件
	 * @param iec104Config
	 * @return
	 */
	Iec104Slave setConfig(Iec104Config iec104Config);
}
