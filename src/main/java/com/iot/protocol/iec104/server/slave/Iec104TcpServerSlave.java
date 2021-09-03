package com.iot.protocol.iec104.server.slave;

import com.iot.protocol.iec104.core.Iec104ThreadLocal;
import com.iot.protocol.iec104.config.Iec104Config;
import com.iot.protocol.iec104.server.Iec104Slave;
import com.iot.protocol.iec104.server.handler.DataHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * iec 104 TCP 协议 服务端做从机
 *
 * @author Mr.Qu
 * @since 2020/5/13
 */
public class Iec104TcpServerSlave implements Iec104Slave {

    private int port;
    private Iec104Config iec104Config;


    private DataHandler dataHandler;

    public Iec104TcpServerSlave(int port) {
        this.port = port;
    }

    /**
     * 启动 从机
     */
    @Override
    public void run() throws Exception {

        Iec104ThreadLocal.setIec104Config(iec104Config);
        //1 EventLoopGroup是一个线程组，它包含了一组nio线程，专门用于网络事件的处理，实际上他们就是Reactor线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
//       这 里创建2个的原因是一个用于服务端接收客户的连接，另一个用于SockentChannel的网络读写。
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 1
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // (3) NIO
                    .channel(NioServerSocketChannel.class)
                    //(4)
                    .childHandler(getIec104ServerInitializer())
                    //(5)  请求的队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // (6) 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //7绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private Iec104ServerInitializer getIec104ServerInitializer() {
        Iec104ServerInitializer iec104ServerInitializer = new Iec104ServerInitializer();
        iec104ServerInitializer.setDataHandler(dataHandler).setIec104Config(iec104Config);
        return iec104ServerInitializer;
    }


    @Override
    public Iec104Slave setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        return this;
    }

    @Override
    public Iec104Slave setConfig(Iec104Config iec104Config) {
        this.iec104Config = iec104Config;
        return this;
    }
}
