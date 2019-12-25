package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.Message;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf message, List<Object> list) throws Exception {
        Message msg = JSONObject.parseObject(message.toString(Charset.defaultCharset()), Message.class);
        list.add(msg);
    }
}
