package com.yangfei.functionasynctcp.client;

import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
public class NettyClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private EventLoopGroup group = new NioEventLoopGroup();

    /**
     *@Fields DELIMITER : 自定义分隔符，服务端和客户端要保持一致
     */
    public static final String DELIMITER = "@@";

    /**
     * @Fields hostIp : 服务端ip
     */
    private String hostIp = "127.0.0.1";

    /**
     * @Fields port : 服务端端口
     */
    private int port= 8888;

    /**
     * @Fields socketChannel : 通道
     */
    private SocketChannel socketChannel;


    /**
     *@Fields clientHandlerInitilizer : 初始化
     */
    @Autowired
    private NettyClientHandlerInitilizer clientHandlerInitilizer;

    /**
     * @Description: 启动客户端
     * @Author:杨攀
     * @Since: 2019年9月12日下午4:43:21
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void start() {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                // 指定Channel
                .channel(NioSocketChannel.class)
                // 服务端地址
                .remoteAddress(hostIp, port)

                // 将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(clientHandlerInitilizer);

        // 连接
        ChannelFuture channelFuture = bootstrap.connect();
        //客户端断线重连逻辑
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    LOGGER.info("连接Netty服务端成功...");
                }else {

                    LOGGER.info("连接Netty服务端失败，进行断线重连...");
                    final EventLoop loop =future.channel().eventLoop();
                    loop.schedule(new Runnable() {
                        @Override
                        public void run() {
                            LOGGER.info("连接正在重试...");
                            start();
                        }
                    }, 20, TimeUnit.SECONDS);
                }
            }


        });

        socketChannel = (SocketChannel) channelFuture.channel();
    }


    /**
     *@Description: 消息发送
     *@Author:杨攀
     *@Since: 2019年9月12日下午5:08:47
     *@param message
     */
    public void sendMsg(String  message) {

        String msg = message.concat(NettyClient.DELIMITER);

        ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);

        ChannelFuture future = socketChannel.writeAndFlush(byteBuf);

        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                if(future.isSuccess()) {
                    System.out.println("===========发送成功");
                }else {
                    System.out.println("------------------发送失败");
                }
            }
        });
    }



    /**
     *@Description: 发送同步消息
     *@Author:杨攀
     *@Since: 2019年9月12日下午5:08:47
     *@param message
     */
    public String sendSyncMsg(String uuid, String  message, SyncFuture<String> syncFuture) {

        String result = "";
        String msg = message.concat(NettyClient.DELIMITER);

        ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);

        try {
            LOGGER.info("发送消息：{}",msg);
            socketChannel.attr(AttributeKey.valueOf("user1")).set(uuid);
            ChannelFuture future = socketChannel.writeAndFlush(byteBuf);
            future.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                    if(future.isSuccess()) {
                        LOGGER.info("===========发送成功");
                        System.out.println();
                    }else {
                        LOGGER.info("------------------发送失败");
                    }
                }
            });

            // 等待 8 秒
            result = syncFuture.get(8, TimeUnit.SECONDS);
            LOGGER.info("发送消息成功后返回信息:{}",result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getHostIp() {
        return hostIp;
    }


    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }


}
