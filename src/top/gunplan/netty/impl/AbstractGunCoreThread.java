package top.gunplan.netty.impl;


import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunCoreCalculatorWorker;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.nio.utils.GunBaseLogUtil;
import top.gunplan.nio.utils.GunBytesUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/**
 * @author dosdrtt
 */
public abstract class AbstractGunCoreThread implements Runnable {
    Selector bootSelector;
    ExecutorService deal;

    public AbstractGunCoreThread(ExecutorService deal) throws IOException {
        bootSelector = Selector.open();
        this.deal = deal;
    }


    public abstract void dealEvent(SelectionKey key) throws Exception;
}



