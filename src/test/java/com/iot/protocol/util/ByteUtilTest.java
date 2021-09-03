package com.iot.protocol.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.fail;

public class ByteUtilTest {

	@Test
	public void testIntToByteArray() {
		byte[] bytes = ByteUtil.intToByteArray(60);
		System.err.println(bytes[0] + " " + bytes[1] + " " + bytes[2] + " " + bytes[3] + " ");
		System.err.println(Integer.toBinaryString(60));
	}

	@Test
	public void testShortToByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testByteArrayToInt() {
		byte[] bytes = new byte[4];
		bytes[0] = 0; // 0000 0111 
		bytes[1] = 104;
		bytes[2] = 0;
		bytes[3] = 0;
		int a = ByteUtil.byteArrayToInt(bytes);
		System.err.println(Integer.toBinaryString(a));
		
		int values =  (bytes[0] & 0xFF) << 24;
		System.err.println(values);
		System.err.println(Integer.toBinaryString(values));
		
	}

	@Test
	public void testByteArrayToShort() {
		int a = 1 << 1;
		System.err.print(a);
	}

	@Test
	public void testListToBytes() {
		fail("Not yet implemented");
	}

	@Test
	public void testDate2HByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testByte2HDate() {
		byte[] dateBytes = new byte[] {0x41, 0x00, 0x33, 0x33, 0x47, 0x42, 0x00};
		Date date = ByteUtil.byte2Hdate(dateBytes);
		System.err.print(date.getTime());
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testByteArrayToHexString() {
		fail("Not yet implemented");
	}

	@Test
	public void testByteArray2HexString() {
		fail("Not yet implemented");
	}

}
