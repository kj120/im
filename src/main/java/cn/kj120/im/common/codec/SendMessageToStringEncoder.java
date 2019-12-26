package cn.kj120.im.common.codec;

import cn.kj120.im.common.message.SendMessage;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class SendMessageToStringEncoder extends MessageToMessageEncoder<SendMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SendMessage sendMessage, List<Object> list) throws Exception {
        String msg = JSONObject.toJSONString(sendMessage);
        list.add(msg);
    }
}
