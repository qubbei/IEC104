package com.iot.protocol.iec104.server.master;


import com.iot.protocol.iec104.config.Iec104Config;
import com.iot.protocol.iec104.server.Iec104MasterFactory;
import org.junit.Test;

public class Iec104TcpMasterClientTest {

	@Test
	public void test() throws  Exception {
		Iec104Config iec104Config  = new Iec104Config();
		iec104Config.setFrameAmountMax((short) 2);
		iec104Config.setTerminalAddress((short) 1);
		Iec104MasterFactory.createTcpClientMaster("10.0.0.118", 2404).setDataHandler(new MasterSysDataHandler()).setConfig(iec104Config).run();
        Thread.sleep(1000000);
	}

}
