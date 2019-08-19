/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * AbstractGunChecker checker transferTarget object
 *
 * @author dosdrtt
 * @see GunNettyChecker
 */
abstract class AbstractGunChecker<Transfer extends GunNetBound> implements GunNettyChecker<Transfer> {
    byte[] src;

    private Throwable exp;

    Transfer to;

    private Object attach;

    private volatile GunNettyChildChannel<SocketChannel> key;

    /**
     * translate
     * @return this chain style
     * @see GunNetBound
     */
    @Override
    public abstract GunNettyChecker<Transfer> translate();

    /**
     * get the source
     *
     * @return bytes
     */
    @Override
    public byte[] source() {
        return src;
    }


    @Override
    public Transfer transferTarget() {
        return to;
    }


    @Override
    public void setTransfer(Transfer to) {
        if (to != null) {
            this.to = to;
        }
    }


    AbstractGunChecker(GunNettyChildChannel<SocketChannel> key) {
        this.key = key;
    }

    @Override
    public GunNettyChildChannel<SocketChannel> channel() {
        return key;
    }

    @Override
    public void setChannel(GunNettyChildChannel<SocketChannel> key) {
        this.key = key;
    }

    @Override
    public Object attachment() {
        return attach;
    }

    @Override
    public void attachment(Object attach) {
        this.attach = attach;
    }


    @Override
    public void setError(Throwable throwable) {
        this.exp = throwable;
    }

    @Override
    public Throwable error() {
        return exp;
    }


}
