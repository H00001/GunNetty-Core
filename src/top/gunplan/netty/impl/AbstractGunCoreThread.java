package top.gunplan.netty.impl;


import java.io.IOException;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import java.util.concurrent.ExecutorService;

/**
 * @author dosdrtt
 */
public abstract class AbstractGunCoreThread implements Runnable {
    Selector bootSelector;
    ExecutorService deal;

    AbstractGunCoreThread(ExecutorService deal) throws IOException {
        bootSelector = Selector.open();
        this.deal = deal;
    }

    public abstract void dealEvent(SelectionKey key) throws Exception;
}



