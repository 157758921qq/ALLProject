package com.yz.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankMsgTest {

    @Test
    void decode() {
        EmbeddedChannel ch = new EmbeddedChannel();
        //加Encoder
        ch.pipeline().addLast(new TankMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(8);

        ch.writeInbound(buf);

        TankMsg tm = ch.readInbound();
        assertEquals(5, tm.x);
        assertEquals(8, tm.y);

    }

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        //加Encoder
        ch.pipeline().addLast(new TankMsgEncoder());
        TankMsg tm = new TankMsg(5, 8);
        //写出去
        ch.writeOutbound(tm);

        //读数据
        ByteBuf buf = ch.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();

        assertEquals(5, x);
        assertEquals(8, y);
    }
}