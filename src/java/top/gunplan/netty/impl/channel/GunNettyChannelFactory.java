/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.channel.pool.GunChildrenChannelPool;
import top.gunplan.netty.impl.channel.pool.GunNettyChannelPool;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.impl.sequence.GunNettySequencer;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelFactory
 *
 * @author frank albert
 * @version 0.0.0.5
 * # 2019-08-08 23:10
 */
public class GunNettyChannelFactory {
    private static final GunNettySequencer BOSS_SEQUENCER = GunNettySequencer.newThreadSafeSequencer();
    private static final GunNettySequencer WORK_SEQUENCER = GunNettySequencer.newThreadSafeSequencer();
    private static final GunChildrenChannelPool POOL = GunNettyChannelPool.INSTANCE;

    public static GunNettyChildChannel<SocketChannel>
    newChannel(final SocketChannel channel,
               final ChannelInitHandle initHandle,
               final GunNettyServerChannel<ServerSocketChannel> pChannel
    ) throws IOException {
        GunNettyChildrenPipeline pipeline = GunNettyChildrenPipeline.newPipeline();
        initHandle.onHasChannel(pipeline);
        pipeline.init();
        GunNettyChildChannel<SocketChannel> data;
        if ((data = POOL.acquireChannelFromPool()) == null) {
            data = new GunNettyChildrenChannelImpl(channel, pipeline, pChannel, BOSS_SEQUENCER.nextSequence());
            //  POOL.addChannelToUse(data);
        }
        return data;//ReflectChannel.ref(data, channel, pipeline, pChannel, BOSS_SEQUENCER.nextSequence());
    }


    public static GunNettyServerChannel<ServerSocketChannel>
    newServerChannel(final ServerSocketChannel channel,
                     final SystemChannelChangedHandle initHandle,
                     final GunConnEventLoop eventLoop) throws IOException {
        assert initHandle != null;
        return new GunNettyServerChannelImpl(channel, initHandle, eventLoop, WORK_SEQUENCER.nextSequence());
    }
}
