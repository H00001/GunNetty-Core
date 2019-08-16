/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.protocol.GunNetInbound;

import java.nio.channels.SocketChannel;


/**
 * GunNettyInputFilterChecker
 *
 * @author dosdrtt
 */
public final class GunNettyInputFilterChecker extends AbstractGunChecker<GunNetInbound> implements GunInboundChecker {


    public GunNettyInputFilterChecker(final GunNettyChildChannel<SocketChannel> channel) {
        super(channel);
    }

    public GunNettyInputFilterChecker(byte[] src, GunNetInbound object) {
        super(null);
        this.src = src;
        this.to = object;
    }

    @Override
    public void translate() {
        this.to.unSerialize(src);
    }


    @Override
    public <R extends GunNetInbound> boolean tranToObject(Class<R> in) {
        try {
            R instance = in.getDeclaredConstructor().newInstance();
            this.to = instance;
            return instance.unSerialize(src);
        } catch (ReflectiveOperationException e) {
            throw new GunException(GunExceptionType.TRANSLATE_ERROR, GunNettyInputFilterChecker.class.getName());
        }
    }

}
