package com.iot.protocol.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Iec104Util工具类测试
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Detail104utilTest {

    @Test
    public void testGetIControl() {
        short accept = 0;
        short send = 0;
        byte[] control = Iec104Util.getIControl(accept, send);
        assertEquals("发送序号不相等", accept, Iec104Util.getAccept(control));
        assertEquals("接收序号不相等", send, Iec104Util.getSend(control));


        accept = 1;
        send = 4;
        control = Iec104Util.getIControl(accept, send);
        assertEquals("发送序号不相等", accept, Iec104Util.getAccept(control));
        assertEquals("接收序号不相等", send, Iec104Util.getSend(control));


        send = 32767;
        accept = send;
        control = Iec104Util.getIControl(accept, send);
        assertEquals("发送序号不相等", accept, Iec104Util.getAccept(control));
        assertEquals("接收序号不相等", send, Iec104Util.getSend(control));
    }


    @Test
    public void testGetSControl() {
        short accept = 0;
        byte[] control = Iec104Util.getSControl(accept);
        assertEquals("接收序号不相等", accept, Iec104Util.getAccept(control));
        short getSControl = 32767;
        control = Iec104Util.getSControl(getSControl);
        control = new byte[]{0x01, 0x00, 0x0A, 0x00};
        assertEquals("接收序不相等", getSControl, Iec104Util.getAccept(control));
    }


    @Test
    public void testGetSend() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetUControl() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetControl() {
        fail("Not yet implemented");
    }

    @Test
    public void testIntToMessageAddress() {
    }

    @Test
    public void testMessageAddressToInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetChanged() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetChangedQualifiers() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetMeaageAttribute() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTransferReasonByte() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsYes() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsTets() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTransferReasonShort() {
        // 传输原因
        short transferReason = 6;
        // true：1 ; false ： 0
        boolean isTest = false;
        // true:0 false;1
        boolean isPN = true;
        byte[] values = ByteUtil.shortToByteArray(Iec104Util.getTransferReasonShort(isTest, isPN, transferReason));
        assertEquals(Iec104Util.getTransferReasonShort(values), transferReason);
        assertEquals(Iec104Util.isYes(values), isPN);
        assertEquals(Iec104Util.isTest(values), isTest);

        /*******************************************/
        // true：1 ; false ： 0
        isTest = true;
        // true:0 false;1
        isPN = false;
        values = ByteUtil.shortToByteArray(Iec104Util.getTransferReasonShort(isTest, isPN, transferReason));
        assertEquals(Iec104Util.getTransferReasonShort(values), transferReason);
        assertEquals(Iec104Util.isYes(values), isPN);
        assertEquals(Iec104Util.isTest(values), isTest);


        /*******************************************/
        transferReason = 32;
        // true：1 ; false ： 0
        isTest = true;
        // true:0 false;1
        isPN = false;
        values = ByteUtil.shortToByteArray(Iec104Util.getTransferReasonShort(isTest, isPN, transferReason));
        assertEquals(Iec104Util.getTransferReasonShort(values), transferReason);
        assertEquals(Iec104Util.isYes(values), isPN);
        assertEquals(Iec104Util.isTest(values), isTest);
    }

    @Test
    public void testGetTerminalAddressByte() {
        short terminalAddress = 1;
        byte[] terminalAddressByte = Iec104Util.getTerminalAddressByte(terminalAddress);
        short newTerminalAddress = Iec104Util.getTerminalAddressShort(terminalAddressByte);
        assertEquals("终端地址解析错误", terminalAddress, newTerminalAddress);
    }
}
