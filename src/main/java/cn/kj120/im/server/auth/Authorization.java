package cn.kj120.im.server.auth;

import cn.kj120.im.server.config.Session;

/**
 * 处理客户端权限
 * @author pcg
 * @date 2019-12-28 18:01:47
 */
public interface Authorization {

    /**
     * 客户端授权
     * @param authString
     * @return
     */
    Session auth(String authString);

    /**
     * 客户端授权信息和用户sessionId 绑定
     * @param authString
     * @param sessionId
     */
    void bind(String authString, String sessionId);
}
