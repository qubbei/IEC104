package com.iot.protocol.iec104.core;

import com.iot.protocol.iec104.message.MessageDetail;
import com.iot.protocol.iec104.util.Encoder104UtilTest;
import com.iot.protocol.util.ByteUtil;
import org.junit.Test;

import java.io.IOException;

public class Decoder104Test {




    @Test
    public  void testSoe() throws IOException {
        MessageDetail messageDetail = Encoder104UtilTest.customInfo();
        byte[] bytes = Encoder104.encoder(messageDetail);
        System.err.println(ByteUtil.byteArrayToHexString(bytes));
        messageDetail = Decoder104.decoder(bytes);
        System.err.println(ByteUtil.byteArrayToHexString(Encoder104.encoder(messageDetail)));


//        68
//        16
//        08 00 02 00
//        0D 　可变结构限定词　１字节　
//        02 01 输原因　２
//        00 01 　应用服务数据单
//        00 03 00
//        00 12
//        34 00 00
//        00 00
//        56
//        7800



        //68
        // 14　　
        // 8c 00 02 00　　
        // 1e 类型标识TI　带CP56Time2a时标的单点信息（遥信带时标  1个字节
        // 01　　可变结构限定词　１字节　　　　　　　　　　可变结构限定词 0000 0001  不连续　　1个结构
        // 03 00 　0000 0011　传输原因　２个字节 突发
        // 01　00 　应用服务数据单元公共地址　　２个字节
        // 0a 00 00　　信息对象地址　３个字节
        // 2d　消息元素　　１个字节
        // ad 28 0b 1c 09 14　　时标7个字节　缺少一个
//        Decoder104.encoder(ByteUtil.hexStringToBytes("68 14 8c 00 02 00 1e 01 03 00 01 00 0a 00 00 2d ad 28 0b 1c 09 14"));
    }

}