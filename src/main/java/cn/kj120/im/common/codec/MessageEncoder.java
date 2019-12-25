package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.Message;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> list) throws Exception {
        String msg = JSONObject.toJSONString(message);
        ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), Charset.defaultCharset());
    }
}
