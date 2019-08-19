/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyPipeline;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * BaseGunNettyChannel
 *
 * @author frank albert
 * @version 0.0.1.1
 * @date 2019-08-09 23:07
 */

abstract class BaseGunNettyChannel<CH extends Channel, LOOP extends GunCoreEventLoop, PL extends GunNettyPipeline> implements GunNettyChannel<CH, LOOP, PL> {
    private final PL pipeline;
    private final long id;
    LOOP eventLoop;
    private CH channel;

    BaseGunNettyChannel(final PL pipeline, final CH channel, final long id) {
        this.pipeline = pipeline;
        this.id = id;
        this.channel = channel;
    }


    @Override
    public GunNettyChannel<CH, LOOP, PL> registerEventLoop(LOOP eventLoop) {
        this.eventLoop = eventLoop;
        return this;
    }


    @Override
    public PL pipeline() {
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
    }
}
