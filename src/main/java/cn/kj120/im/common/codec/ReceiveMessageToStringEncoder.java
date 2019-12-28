package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.ReceiveMessage;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReceiveMessageToStringEncoder extends MessageToMessageEncoder<ReceiveMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ReceiveMessage receiveMessage, List<Object> list) throws Exception {
        String msg = JSONObject.toJSONString(receiveMessage);
        list.add(msg);
    }
}
