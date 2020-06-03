package com.yz.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankMsgEncoder extends MessageToByteEncoder<TankMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TankMsg tankMsg, ByteBuf byteBuf) throws Exception {
        //将tankMsg通过writeInt的方式写到字节数组中
        //一共写了8个字节，4个字节 +  4个字节
        //System.out.println("encoder:"+tankMsg);
        byteBuf.writeInt(tankMsg.x);
        byteBuf.writeInt(tankMsg.y);

    }
}
