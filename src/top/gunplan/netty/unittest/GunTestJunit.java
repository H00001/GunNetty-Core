package top.gunplan.netty.unittest;


import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.filters.GunNettyStdFirstFilter;
import top.gunplan.netty.filters.GunStdHttp2Filter;
import top.gunplan.netty.handles.GunStdHttpHandle;
import top.gunplan.netty.impl.GunBootServerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author dosdrtt
 */
public class GunTestJunit {


    public static void main(String[] args) {


        GunBootServer server = GunBootServerFactory.getInstance();
        ExecutorService es0 = new ThreadPoolExecutor(100, 1000,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        ExecutorService es1 = new ThreadPoolExecutor(100, 1000,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        server.setExecuters(es0, es1).getPipeline().addFilter(new GunNettyStdFirstFilter()).
                addFilter(new GunStdHttp2Filter()).
                //  addFilter(new GunHttpdHostCheck()).
                        setHandle(new GunStdHttpHandle("top.gunplan.netty.test"));
        try {
            server.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}