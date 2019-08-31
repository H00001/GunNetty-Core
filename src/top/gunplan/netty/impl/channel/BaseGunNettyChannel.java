/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.pipeline.GunNettyPipeline;
import top.gunplan.netty.impl.sequence.GunNettySequencer;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    List<GunNettyTimer> timers;
    GunNettySequencer unsafeSequencer = GunNettySequencer.newThreadUnSafeSequencer();
    private ScheduledExecutorService scheduledExecutorService = GunNettyExecutors.newScheduleExecutorPool();
    private CH channel;

    BaseGunNettyChannel(final PL pipeline, final CH channel, final long id) {
        this.pipeline = pipeline;
        this.id = id;
        this.channel = channel;
        timers = pipeline != null ? pipeline.timers() : null;
        scheduledExecutorService.scheduleAtFixedRate(this::time, 100, 100, TimeUnit.MILLISECONDS);
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

    public abstract void time();


    @Override
    public void destory() {
        scheduledExecutorService.shutdown();
        System.gc();
    }

    List timers() {
        return timers;
    }
}
