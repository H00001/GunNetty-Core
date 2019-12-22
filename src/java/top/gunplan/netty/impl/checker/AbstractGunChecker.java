/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.checker;

import top.gunplan.netty.impl.GunNetBound;
import top.gunplan.netty.impl.GunNettyChecker;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * AbstractGunChecker checker transferTarget object
 *
 * @author dosdrtt
 * @see GunNettyChecker
 */
public abstract class AbstractGunChecker<Transfer extends GunNetBound> implements GunNettyChecker<Transfer> {
    ByteBuffer src;

    private Throwable exp;

    Transfer to;

    private Object attach;

    private volatile GunNettyChildChannel<SocketChannel> channel;

    /**
     * translate
     *
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
    public ByteBuffer source() {
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
        this.channel = key;
    }

    @Override
    public GunNettyChildChannel<SocketChannel> channel() {
        return channel;
    }

    @Override
    public void setChannel(GunNettyChildChannel<SocketChannel> channel) {
        this.channel = channel;
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
