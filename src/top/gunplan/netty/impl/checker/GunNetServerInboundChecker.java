/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.checker;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.impl.GunNettyChecker;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.protocol.GunNetInbound;

import java.nio.channels.SocketChannel;


/**
 * GunNetServerInboundChecker
 *
 * @author dosdrtt
 */
public final class GunNetServerInboundChecker extends AbstractGunChecker<GunNetInbound> implements GunInboundChecker {


    public GunNetServerInboundChecker(final GunNettyChildChannel<SocketChannel> channel) {
        super(channel);
    }


    @Override
    public GunNettyChecker<GunNetInbound> translate() {
        this.to.unSerialize(src);
        return this;
    }


    @Override
    public <R extends GunNetInbound> boolean tranToObject(Class<R> in) {
        try {
            R instance = in.getDeclaredConstructor().newInstance();
            this.to = instance;
            return instance.unSerialize(src);
        } catch (ReflectiveOperationException e) {
            throw new GunException(GunExceptionType.TRANSLATE_ERROR, GunNetServerInboundChecker.class.getName());
        }
    }

}
