/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.channel.GunNettyChildChannel;

/**
 * SystemChannelChangedHandle
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 09:28
 */
public interface SystemChannelChangedHandle {
    void whenInit(GunNettyParentPipeline pipeline);

    void hasChannel(GunNettyChildChannel channel);
}
