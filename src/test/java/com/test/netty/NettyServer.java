package com.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.Future;

public class NettyServer {


        public static void main(String[] args) throws Exception {
            //负责接客,不停处理事件的线程组
            EventLoopGroup bossGroup = new NioEventLoopGroup(2);

            //负责服务
            EventLoopGroup workerGroup = new NioEventLoopGroup(4);

            //Server启动辅助类
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup);

            //指定channel类型， 基于事件处理的 异步全双工
            b.channel(NioServerSocketChannel.class);

            //netty 帮我们内部处理了accept的过程
            //写一个钩子函数callback
            //一个server接收进来的客户端都认为是childhandler
            b.childHandler(new MyChildInitializer());
            ChannelFuture future = b.bind(8880).sync();
            future.channel().closeFuture().sync();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    /**
     * Netty 帮我们把客户端迎接进来
     * 会自动调用initChannel()
     * 这个socketChannel就是产生好的那个连接
     * 从这个socketChannel里写和读数据
     *
     */
    class MyChildInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println("a client connected!");
            //又注册一个用来处理数据的机器人
            //管道上加好多处理器
            socketChannel.pipeline().addLast(new MyChildHandler());
        }
    }

    /**
     * ChannelInboundHandlerAdapter
     * 处理连上我的
     */

    class MyChildHandler extends ChannelInboundHandlerAdapter {

        //channelRead:代表有客户端连上来了，而且向server写了个数据  并且Netty将数据收了下来
        //封装在msg中
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //这个Object msg最原始的类型是ByteBuf
            ByteBuf buf = (ByteBuf)msg;
            System.out.println(buf.toString());
            //将收到的数据沿着这个通道，又写回去
            ctx.writeAndFlush(msg);
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }


}
