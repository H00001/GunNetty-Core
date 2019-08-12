/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * BaseGunNettyChannel
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-09 23:07
 */

public abstract class BaseGunNettyChannel<CH extends Channel, LOOP extends GunCoreEventLoop> implements GunNettyChannel<CH, LOOP> {
    private final GunNettyPipeline pipeline;
    private final long id;
    LOOP eventLoop;
    private CH channel;

    BaseGunNettyChannel(final GunNettyPipeline pipeline, final CH channel, final long id, final LOOP eventLoop) {
        this.pipeline = pipeline;
        this.id = id;
        this.channel = channel;
        this.eventLoop = eventLoop;
    }


    @Override
    public GunNettyChannel<CH, LOOP> registerEventLoop(LOOP eventLoop) {
        this.eventLoop = eventLoop;
        return this;
    }


    @Override
    public GunNettyPipeline pipeline() {
        return pipeline;
    }


    @Override
    public long channelId() {
        return id;
    }

    @Override
    public CH channel() {
        return channel;
    }


    @Override
    public void close() throws IOException {
        channel.close();
        eventLoop.fa();
    }
}
