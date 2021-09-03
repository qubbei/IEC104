package com.iot.protocol.iec104.server;

import com.iot.protocol.iec104.server.slave.Iec104TcpServerSlave;

/**
 * 104从机工厂
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class Iec104SlaveFactory {

    /**
     * @param port 端口 从机端口
     * @return Iec104Slave
     * @Title: createTcpServerSlave
     * 生产一个 iec104 协议TCP传输方式服务端做从机服务
     */
    public static Iec104Slave createTcpServerSlave(int port) {
        return new Iec104TcpServerSlave(port);
    }
}
