package cn.kj120.im.server;

import cn.kj120.im.server.server.NioServer;

public class Bootstrap {

    public static void main(String[] args) throws InterruptedException {
        new NioServer().start(996);
    }
}
