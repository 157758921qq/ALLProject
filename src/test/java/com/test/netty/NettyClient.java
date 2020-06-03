package com.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        //用来处理连接上之后的连接事件
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);

        //Channel建立好了后，会帮调ChannelInitializer
        //加入我们自己的处理器MyHandle
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

                socketChannel.pipeline().addLast(new MyHandler());
            }
        });

        //.sync()确认连接，是阻塞的
        ChannelFuture future = b.connect("localhost", 8888).sync();


        //等待关闭
        //future.channel拿到channel
        //future.channel().closeFuture()等待，是否有人调用channel的close方法，返回结果类型是futrue
        //如果没人调用close方法，这个哥们就一直等待
        //等待关闭
        future.channel().closeFuture().sync();



        System.out.println("go on");
        workerGroup.shutdownGracefully();
    }



    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //刚连接上时，刚初始化好的时候，往外发送一个数据
            //Netty所有的数据必须包装成ByteBuf，使用辅助类Unpooled.copiedBuffer(字节数组)
            ByteBuf buf = Unpooled.copiedBuffer("yangwlz".getBytes());
            ctx.writeAndFlush(buf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //怎么读，怎么解析
            System.out.println(msg.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
