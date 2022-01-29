package com.melo.left.training.java.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

/**
 * bytebuffer.default.direct.heap
 */
@Slf4j
public class HttpServer {

    private boolean ssl;
    private int port;

    public HttpServer(boolean ssl, int port) {
        this.ssl = ssl;
        this.port = port;
    }

    public void run() throws Exception {
        final SslContext sslContext;
        if (ssl) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslContext = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslContext = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(3);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1000);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //网络的参数
            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    //Nagle算法开启 能把小包拼成大块
                    .option(ChannelOption.TCP_NODELAY, true)
                    //长链接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //TIme_wait复用地址和端口
                    .option(ChannelOption.SO_REUSEADDR, true)
                    //http的接收区和发送区缓冲区大小 32k
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    //设置在worker上
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //加上NioServerSocketChannel，表示里面都用nio操作
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //关键点：代理服务器的参数传入进去 pipeline上加东西
                    .childHandler(new HttpInitializer(sslContext));
            Channel channel = bootstrap.bind(port).sync().channel();
            log.info("启动netty http服务，监听地址和端口为" + (ssl ? "https" : "http") + "://127.0.0.1" + port);
            //所有操作是异步的，所以加个同步sync，会卡住进程不结束 加上显示的同步操作
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
