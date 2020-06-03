package com.yz.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        //TCP底层的麻烦地
        //TCP的拆包 和 粘包
        //首先，要判断下：接收到的包的长度，是否是我们发的包的长度
        if(buf.readableBytes() < 8) return;
        //先写的先读，
        int x = buf.readInt();
        int y = buf.readInt();

        //转成TankMsg，加到这个List<Object>中
        out.add(new TankMsg(x,y));

    }
}
