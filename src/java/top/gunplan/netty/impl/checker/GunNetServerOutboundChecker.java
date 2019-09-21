/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.checker;

import top.gunplan.netty.impl.GunNettyChecker;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SocketChannel;

/**
 * @author dosdrtt
 * @see GunNettyChecker
 */
public final class GunNetServerOutboundChecker extends AbstractGunChecker<GunNetOutbound> implements GunOutboundChecker {
    private boolean hasDataToOutput;

    /**
     * GunNetServerOutboundChecker
     *
     * @param outputObject GunNetOutbound
     * @param channel      SelectionKey
     */
    public GunNetServerOutboundChecker(GunNetOutbound outputObject, GunNettyChildChannel<SocketChannel> channel) {
        super(channel);
        this.to = outputObject;
        hasDataToOutput = outputObject != null;
    }


    @Override
    public GunNettyChecker<GunNetOutbound> translate() {
        this.src = to.serialize();
        hasDataToOutput = src != null && src.length != 0;
        return this;
    }


    @Override
    public boolean isHasDataToOutput() {
        translate();
        return hasDataToOutput;
    }
}
