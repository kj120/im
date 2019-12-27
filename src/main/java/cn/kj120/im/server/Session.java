package cn.kj120.im.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 会话信息
 * @param <T> 其他业务数据
 */
@Data
@AllArgsConstructor
public class Session<T> {

    private String sessionId;

    private Date connectDate;

    private T data;

    public Session(String sessionId, Date connectDate) {
        this.sessionId = sessionId;
        this.connectDate = connectDate;
    }
}
