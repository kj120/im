package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.ReceiveMessage;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringToReceiveMessageDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String s, List<Object> list) throws Exception {
        ReceiveMessage receiveMessage = JSONObject.parseObject(s, ReceiveMessage.class);
        list.add(receiveMessage);
    }
}
