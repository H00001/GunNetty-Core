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
public final class GunNettyOutputFilterChecker extends AbstractGunChecker<GunNetOutbound> implements GunOutboundChecker {
    private boolean hasDataToOutput;

    /**
     * GunNettyOutputFilterChecker
     *
     * @param outputObject GunNetOutbound
     * @param channel      SelectionKey
     */
    public GunNettyOutputFilterChecker(GunNetOutbound outputObject, GunNettyChildChannel<SocketChannel> channel) {
        super(channel);
        this.to = outputObject;
        hasDataToOutput = outputObject != null;
    }


    @Override
    public void translate() {
        if (isHasDataToOutput()) {
            this.src = to.serialize();
        }
        hasDataToOutput = src.length != 0;
    }


    @Override
    public boolean isHasDataToOutput() {
        return hasDataToOutput;
    }
}
