# Iec 104

## 目录

- [项目介绍](#项目介绍)
- [使用](#使用)
- [其他](#其他)
- [感谢](#感谢)
- [联系](#联系)


## 项目介绍
          该项目基于Netty实现的底层网络通信，支持从站服务端、主站客户端的模式, 可以通过Master采集数据，  
        也可以通过Slave模拟终端。该项目已经支持: S帧、U帧、总召唤指令、沾包拆包等功能，由于104协议部分内容  
        存在可扩展性，因此本项目只完成通讯部分以及协议的解码和转码部分。在实际使用过程中需要按照硬件厂商提供  
        的点表再将消息部分装换成可识别的业务对象。
## 使用
### 主站
```
    // 创建一个配置文件
    Iec104Config iec104Config  = new Iec104Config();
    // 指定收到多少帧就回复一个S帧
    iec104Config.setFrameAmountMax((short) 1);
    // 终端地址需要和从站保持一致
    iec104Config.setTerminnalAddress((short) 1);
    Iec104MasterFactory.createTcpClientMaster("127.0.0.1", 2404).setDataHandler(new SysDataHandler()).setConfig(iec104Config).run();
```
### 从站
```
    // 创建一个配置文件
    Iec104Config iec104Config  = new Iec104Config();
    // 指定收到多少帧就回复一个S帧
    iec104Config.setFrameAmountMax((short) 1);
    // 终端地址
    iec104Config.setTerminnalAddress((short) 1);
   Iec104SlaveFactory.createTcpServerSlave(2404).setDataHandler(new SysDataHandler()).setConfig(iec104Config).run();
```
### 自定义DataHandler
```
 public class SysDataHandler implements DataHandler {

	@Override
	public void handlerAdded(ChannelHandler ctx) throws Exception {
        // 连接成功后
	}

	@Override
	public void channelRead(ChannelHandler ctx, MessageDetail Detail104) throws Exception {
        // 收到消息后
        
        // 注意 往ctx.writeAndFlush中存放的应该是自己封装的 MessageDetail对象
        ctx.writeAndFlush(BasicInstruction104.getEndGeneralCallDetail104());
	}

```
### 其他
 项目相关的技术问题、缺陷报告、建议等信息请通过 Issue 发布 
## 感谢
感谢mujave提供的iec项目 https://github.com/mujave/iec
## 联系
E-mail:1228753336@qq.com
