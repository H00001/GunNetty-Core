/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.ChannelInitHandle;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-08 23:10
 */
public class GunNettyChannelFactory {
    private static final GunNettySequencer BOSS_SEQUENCER = GunNettySequencer.newThreadSafeSequencer();

    public static GunNettyChannel<SocketChannel> newChannel(SocketChannel channel, ChannelInitHandle initHandle) {
        GunNettyPipeline pipeline = GunNettyPipeline.newPipeline();
        initHandle.onHasChannel(pipeline);
        return new GunNettyChannelImpl(channel, pipeline, BOSS_SEQUENCER.nextSequence());
    }
}
