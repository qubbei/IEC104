package com.iot.protocol.iec104.server;

import com.iot.protocol.iec104.server.master.Iec104TcpClientMaster;

/**
 * IEC104规约主站 工厂类
 *
 * @author Mr.Qu
 * @since 2020/5/19
 */
public class Iec104MasterFactory {


    /**
     * @param host 从机地址
     * @param port 端口
     * @return
     * @Title: createTcpClientMaster
     * 创建一个TCM客户端的104主站
     */
    public static Iec104Master createTcpClientMaster(String host, int port) {
        return new Iec104TcpClientMaster(host, port);
    }
}
