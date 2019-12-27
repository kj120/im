package cn.kj120.im.server.auth;

import cn.kj120.im.server.config.Session;
import cn.kj120.im.server.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class NonAuthorization implements Authorization {

    @Override
    public Session auth(String authString) {
        log.info("授权字符串: {}", authString);
        return new Session(SessionUtil.createSessionId(), new Date());
    }
}
