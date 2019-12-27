package cn.kj120.im.server.server;

public interface Server {

    void start(int port) throws InterruptedException;

    void shop();
}
