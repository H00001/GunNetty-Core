package top.gunplan.netty.unittest;


import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.filters.GunHttpdHostCheck;
import top.gunplan.netty.filters.GunStdHttp2Filter;
import top.gunplan.netty.handles.GunStdHttpHandle;
import top.gunplan.netty.impl.GunBootServerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//非常感谢对字节跳动的关注， 请在这里投递下简历吧，我们收到后会进行筛选，祝你好运 【投递简历： https://dwz.cn/8hFYq7YK】

/**
 * @author dosdrtt
 */
public class GunTestJunit {


    public static void main(String[] args) throws IOException {


        GunBootServer server = GunBootServerFactory.getInstance();
        ExecutorService es0 = new ThreadPoolExecutor(4, 4,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        ExecutorService es1 = new ThreadPoolExecutor(4, 4,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        server.setExecuters(es0, es1).getPipeline().addFilter(new GunStdHttp2Filter()).setHandle(new GunStdHttpHandle("top.gunplan.netty.test")).addFilter(new GunHttpdHostCheck());
        try {
            server.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}