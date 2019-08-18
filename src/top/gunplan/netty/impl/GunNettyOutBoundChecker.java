/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunOutboundChecker;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SocketChannel;

/**
 * @author dosdrtt
 * @see GunNettyChecker
 */
public final class GunNettyOutBoundChecker extends AbstractGunChecker<GunNetOutbound> implements GunOutboundChecker {
    private boolean hasDataToOutput;

    /**
     * GunNettyOutBoundChecker
     *
     * @param outputObject GunNetOutbound
     * @param channel      SelectionKey
     */
    public GunNettyOutBoundChecker(GunNetOutbound outputObject, GunNettyChildChannel<SocketChannel> channel) {
        super(channel);
        this.to = outputObject;
        hasDataToOutput = outputObject != null;
    }


    @Override
    public GunNettyChecker<GunNetOutbound> translate() {
        if (isHasDataToOutput()) {
            this.src = to.serialize();
        }
        hasDataToOutput = src.length != 0;
        return this;
    }


    @Override
    public boolean isHasDataToOutput() {
        return hasDataToOutput;
    }
}
