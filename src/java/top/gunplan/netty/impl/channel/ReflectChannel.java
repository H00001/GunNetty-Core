/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

import java.lang.invoke.MethodHandles;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public final class ReflectChannel {
    //   private static final VarHandle pipelineHandle;
////    private static final VarHandle pChannelHandle;

    static {
        MethodHandles.Lookup look = MethodHandles.lookup();
        //  try {
        //          pipelineHandle = look.findVarHandle(GunNettyChildChannel.class, "", SocketChannel.class);
        //          channelHandle = look.findVarHandle(GunNettyChildrenPipelineImpl.class, "pipeline", GunNettyChildrenPipeline.class);
        //          pChannelHandle = look.findVarHandle(, "", GunNettyServerChannel<ServerSocketChannel>.class);

        //    } catch (ReflectiveOperationException e) {
        //         e.printStackTrace();
        //    }

        // }
    }

    public static GunNettyChildChannel<SocketChannel> ref(final GunNettyChildChannel<SocketChannel> c,
                                                          final SocketChannel channel,
                                                          final GunNettyChildrenPipeline pipeline,
                                                          final GunNettyServerChannel<ServerSocketChannel> pChannel,
                                                          final long seq) {
        //       VarHandle.
        return null;
    }

}