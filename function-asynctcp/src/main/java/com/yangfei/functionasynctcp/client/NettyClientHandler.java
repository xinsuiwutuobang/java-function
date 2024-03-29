package com.yangfei.functionasynctcp.client;

import java.util.concurrent.TimeUnit;

import com.yangfei.functionasynctcp.service.NettyClientService;
import io.netty.buffer.ByteBuf;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

@Component
@ChannelHandler.Sharable // 标注一个channel handler可以被多个channel安全地共享
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    @Autowired
    private NettyClientService service;

    @Autowired
    private NettyClient nettyClient;

    /**
     * 自定义了解码器，由于没有结束符，会导致cahnnelRead0一直不执行。
     * @Description: 服务端发生消息给客户端，会触发该方法进行接收消息
     * @Author:杨攀
     * @Since: 2019年9月12日下午5:03:31
     * @param ctx
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {

        String msg = byteBuf.toString(CharsetUtil.UTF_8);

        LOGGER.info("channelRead0 客户端收到消息：{}", msg);
        Channel channel = ctx.channel();
        String uuid = (String) channel.attr(AttributeKey.valueOf("user1")).get();

        LOGGER.info(uuid);

        //service.ackMsg(msg);
        service.ackSyncMsg(uuid,msg); // 同步消息返回
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("请求连接成功...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        LOGGER.info("连接被断开...");

        // 使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {

            @Override
            public void run() {
                // 重连
                nettyClient.start();
            }
        }, 20, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    /**
     * 处理异常, 一般将实现异常处理逻辑的Handler放在ChannelPipeline的最后
     * 这样确保所有入站消息都总是被处理，无论它们发生在什么位置，下面只是简单的关闭Channel并打印异常信息
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        // 输出到日志中
        //ExceptionUtil.getStackTrace(cause);

        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

}