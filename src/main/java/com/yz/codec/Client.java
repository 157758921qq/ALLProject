package com.yz.codec;

import com.yz.chat.ClientFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

    private Channel channel = null;

    public void connect() {
        //用来处理连接上之后的连接事件
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);

            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //初始化 通道
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new TankMsgEncoder())
                            .addLast(new Myhandler());
                }
            });

            ChannelFuture future = b.connect("localhost", 8888).sync();
            System.out.println("Connected to Server");

            //等待关闭
            future.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    /**
     * 数据发送出去，必须拿到那个 通道
     * 处理：将通道表露出来变成  成员变量
     *
     */
    public void send(String text) {
        //转发：都需要转成 ByteBuf，使用Unpooled.copiedBuffer()
        //网络上只能传：字节数组，所以这里需要将  "字符串"  转成  ByteBuf
        channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
    }

    public void closeConnection() {
        send("--bye--");
        channel.close();
    }


    static class Myhandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //刚连接上时
            //Netty所有的数据必须包装成ByteBuf，使用辅助类Unpooled.copiedBuffer(字节数组)
            //ByteBuf buf = Unpooled.copiedBuffer("yangwlz".getBytes());
            //writeAndFlush(对象)肯定是报错的
            ctx.writeAndFlush(new TankMsg(5, 8));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = null;
            try {
                //字节数组 转成  字符串
                //ByteBuf的回收？？？？？？直接内存，不归JVM管
                buf = (ByteBuf) msg;
                byte[] bytes = new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(), bytes);
                String str = new String(bytes);

                System.out.println(str);
                //这里是 重点直接内存的回收
                //垃圾内存的回收 Reference Count，引用计数
                System.out.println(buf.refCnt());
                //将收到的内容  在Frame中显示出来。
                ClientFrame.INSTANCE.updateText(str);
            } finally {
                if(buf != null){
                    //当这块内存的 引用计算为0 时，释放内存
                    ReferenceCountUtil.release(buf);
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }


    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }


}
