package cn.kj120.im.server.auth;

import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class NonAuthorization implements Authorization<String> {

    @Override
    public Session<String> auth(String authString) {
        log.info("授权字符串: {}", authString);
        return new Session(SessionUtil.createSessionId(), new Date());
    }

    @Override
    public void bind(String authString, String sessionId) {
        log.info("授权字符串: {} , sessionId: {} 绑定成功", authString, sessionId);
    }
}
