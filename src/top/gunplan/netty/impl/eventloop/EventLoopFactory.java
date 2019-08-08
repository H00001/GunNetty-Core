/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * EventLoopFactory
 *
 * @author frank albert
 * @version 2.0.0.1
 * @date 2019-07-21 20:27
 */

public final class EventLoopFactory {
    public static GunNettyTransfer<GunNettyChannel<SocketChannel>> newGunNettyBaseTransfer() {
        return new GunNettyBaseTransferEventLoopImpl<>();
    }

    public static GunNettyTransfer<GunNettyChannel<SocketChannel>> newGunDisruptorTransfer() {
        return new GunNettyDisruptorTransferEventLoopImpl<>();
    }

    public static DataEventLoopBuilder buildDataEventLoop(int sum) {
        return new DataEventLoopBuilderImpl(sum);
    }

    public static ConnEventLoopBuilder buildConnEventLoop() throws IOException {
        return new ConnEventLoopBuilderImpl();
    }

    static final class ConnEventLoopBuilderImpl implements ConnEventLoopBuilder {
        final GunConnEventLoop eventLoop;

        ConnEventLoopBuilderImpl() throws IOException {
            this.eventLoop = new GunCoreConnectionEventLoopImpl();
        }

        @Override
        public EventLoopBuilder<GunConnEventLoop> with(ExecutorService deal, ChannelInitHandle handle) throws IOException {
            with(deal);
            this.eventLoop.initInitHandle(handle);
            return this;
        }

        @Override
        public GunConnEventLoop build() {
            return eventLoop;
        }

        @Override
        public EventLoopBuilder<GunConnEventLoop> andRegister(GunNettyCoreThreadManager manager) {
            eventLoop.registerManager(manager);
            return this;
        }

        @Override
        public EventLoopBuilder<GunConnEventLoop> with(ExecutorService deal) throws IOException {
            this.eventLoop.init(deal);
            return this;
        }

        @Override
        public ConnEventLoopBuilder bindPort(int p) {
            eventLoop.openPort(p);
            return this;
        }
    }

    static final class DataEventLoopBuilderImpl implements DataEventLoopBuilder {
        final GunDataEventLoop<SocketChannel>[] eventLoops;
        final int sum;

        DataEventLoopBuilderImpl(int sum) {
            this.sum = sum;
            eventLoops = new GunCoreDataEventLoopImpl[sum];
            for (int i = 0; i < sum; i++) {
                eventLoops[i] = new GunCoreDataEventLoopImpl();
            }
        }

        @Override
        public GunDataEventLoop<SocketChannel>[] build() {
            return eventLoops;
        }


        @Override
        public DataEventLoopBuilder andRegister(GunNettyCoreThreadManager manager) {
            for (int i = 0; i < sum; i++) {
                eventLoops[i].registerManager(manager);
            }
            return this;
        }

        @Override
        public DataEventLoopBuilder with(ExecutorService deal) throws IOException {
            for (int i = 0; i < sum; i++) {
                eventLoops[i].init(deal);
            }
            return this;
        }

    }
}
