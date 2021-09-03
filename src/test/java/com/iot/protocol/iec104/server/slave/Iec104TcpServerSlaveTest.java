package com.iot.protocol.iec104.server.slave;


import com.iot.protocol.iec104.config.Iec104Config;
import com.iot.protocol.iec104.server.Iec104SlaveFactory;
import org.junit.Test;

/**
 * 测试 iec104 协议TCP传输方式服务端做从机服务
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Iec104TcpServerSlaveTest {

    /**
     * 测试 iec104 协议TCP传输方式服务端做从机服务
     */
    @Test
    public void test() throws Exception {
        Iec104Config iec104Config = new Iec104Config();
        iec104Config.setFrameAmountMax((short) 8);
        iec104Config.setTerminalAddress((short) 1);
        Iec104SlaveFactory.createTcpServerSlave(2404).setDataHandler(new SysDataHandler()).setConfig(iec104Config).run();
//        Thread.sleep(1000000);
    }

}
