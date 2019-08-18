/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.impl.pipeline.GunNettyParentPipeline;
import top.gunplan.netty.impl.sequence.GunNettySequencer;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelFactory
 *
 * @author frank albert
 * @version 0.0.0.4
 * @date 2019-08-08 23:10
 */
public class GunNettyChannelFactory {
    private static final GunNettySequencer BOSS_SEQUENCER = GunNettySequencer.newThreadSafeSequencer();
    private static final GunNettySequencer WORK_SEQUENCER = GunNettySequencer.newThreadSafeSequencer();

    public static GunNettyChildChannel<SocketChannel>
    newChannel(final SocketChannel channel,
               final ChannelInitHandle initHandle,
               final GunNettyServerChannel<ServerSocketChannel> pChannel,
               final GunDataEventLoop<SocketChannel> eventLoop) {
        GunNettyChildrenPipeline pipeline = GunNettyChildrenPipeline.newPipeline();
        initHandle.onHasChannel(pipeline);
        return new GunNettyChannelImpl(channel, pipeline, pChannel, eventLoop, BOSS_SEQUENCER.nextSequence());
    }


    public static GunNettyServerChannel<ServerSocketChannel>
    newServerChannel(final ServerSocketChannel channel,
                     final SystemChannelChangedHandle initHandle,
                     final GunConnEventLoop eventLoop) {
        GunNettyParentPipeline pipeline = GunNettyParentPipeline.newPipeline();
        initHandle.whenInit(pipeline);
        return new GunNettyServerChannelImpl(channel, pipeline, eventLoop, WORK_SEQUENCER.nextSequence());
    }
}
