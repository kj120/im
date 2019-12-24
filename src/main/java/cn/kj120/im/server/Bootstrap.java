package cn.kj120.im.server;

public class Bootstrap {

    public static void main(String[] args) {
        new NioServer().start(996);
    }
}
