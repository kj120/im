package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.Message;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class StringToMessageDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String message, List<Object> list) throws Exception {
        Message msg = JSONObject.parseObject(message, Message.class);
        list.add(msg);
    }
}
