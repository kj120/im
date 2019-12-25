package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.SendMessage;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StringToMessageDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String message, List<Object> list) throws Exception {
        SendMessage msg = JSONObject.parseObject(message, SendMessage.class);
        list.add(msg);
    }
}
