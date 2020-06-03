package com.yz.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;

public class Server {

    /**
     * Netty内部保存 客户端和server连接 ChannelGroup
     * 每当有client连接上来在初始化时都会生成一个SocketChannel，这还不够，这个channel能不能用还不清楚
     * channel已经启动，并且这个channel是可用的
     * 加到ChannelGroup中就OK
     * 弄清楚ChannelHandlerContext ctx 与  ChannelGroup clients的区别
     */

    public ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        //负责接客,不停处理事件的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            //Server启动辅助类
            ServerBootstrap b = new ServerBootstrap();
//指定channel类型， 基于事件处理的 异步全双工
            ChannelFuture future = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)

                    //netty 帮我们内部处理了accept的过程
                    //写一个钩子函数callback
                    //一个server接收进来的客户端都认为是childhandler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline()
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888)
                    .sync();

            //service启动提示
            ServerFrame.INSTANCE.updateClientMsg("Server started!");
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    class ServerChildHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //这个方法确认channel已经启动，并且这个channel是可用的情况下
            //SocketAddress socketAddress = ctx.channel().remoteAddress();  ip+port
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();

            String ip = inetSocketAddress.getAddress().getHostAddress();
            int port = inetSocketAddress.getPort();
            String hostName = inetSocketAddress.getHostName();
            System.out.println("主机名：" + hostName + "--ip:" + ip + "--端口:" + port);

            //将channel的引用加到List中
            clients.add(ctx.channel());
        }

        //channelRead:代表有客户端连上来了，而且向server写了个数据  并且Netty将数据收了下来
        //封装在msg中
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            TankMsg tm = (TankMsg)msg;
            ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            //当每个客户端连接上来的channel发生异常后
            //从client中去除
            //并且 关闭这个channel通道

            //心跳检查： 比如1个小时没有收到客户端发来的消息，就认为client端死了，关闭这个通道
            clients.remove(ctx.channel());
            ctx.close();
        }
    }

}

