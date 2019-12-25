package cn.kj120.im.client;

import cn.kj120.im.common.message.SendMessage;

import java.util.List;

public interface Client {

    void connect(String host, int port);

    void send(String message, String to);

    void sendBatch(String message, List<String> tos);

    void sendAll(String message);

    void onMessage(SendMessage sendMessage);

    void onOpen(String host, int port);

    void onError(String error);

    void onClose();
}