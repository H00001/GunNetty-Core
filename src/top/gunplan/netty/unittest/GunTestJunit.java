package top.gunplan.netty.unittest;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.filters.GunStdHttp2Filter;
import top.gunplan.netty.filters.GunStdToStringFilter;
import top.gunplan.netty.handles.GunStdHttpHandle;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.example.GunOutputHander;

import java.io.IOException;
import java.util.concurrent.*;


/**
 * @author dosdrtt
 */
public class GunTestJunit {


    public static void main(String[] args) throws IOException {
        GunBootServer server = GunBootServerFactory.getInstance();
        ExecutorService es0= new ThreadPoolExecutor(4,4,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        ExecutorService es1= new ThreadPoolExecutor(4,4,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        server.setExecuters(es0, es1).addFilter(new GunStdHttp2Filter()).setHandel(new GunStdHttpHandle("top.gunplan.netty.test"));
        try {
            server.sync();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}