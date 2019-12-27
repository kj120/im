package cn.kj120.im.server.auth;

import cn.kj120.im.server.config.Session;


public interface Authorization<T> {

    Session<T> auth(String authString);
}
