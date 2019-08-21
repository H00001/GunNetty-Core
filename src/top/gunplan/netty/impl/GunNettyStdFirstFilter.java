/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunFunctionMapping;
import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.filter.GunNettyConnFilter;
import top.gunplan.netty.filter.GunNettyDataFilter;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.impl.checker.AbstractGunChecker;
import top.gunplan.netty.impl.checker.GunInboundChecker;
import top.gunplan.netty.impl.checker.GunOutboundChecker;
import top.gunplan.utils.GunBytesUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * GunNettyStdFirstFilter First Input Filter and Last Output Filter
 * this class is high dangerous
 *
 * @author dosdrtt
 */
@GunNetFilterOrder
public final class GunNettyStdFirstFilter implements GunNettyDataFilter, GunNettyConnFilter {

    private final GunNettyBaseObserve observe;
    private static Field operatorSrc;

    static {
        try {
            operatorSrc = AbstractGunChecker.class.getDeclaredField("src");
            assert operatorSrc != null;
        } catch (NoSuchFieldException ignore) {

        }
        operatorSrc.setAccessible(true);

    }

    public GunNettyStdFirstFilter(GunNettyBaseObserve baseObserve) {
        this.observe = baseObserve;
    }


    private void dealCloseEvent(SocketAddress key, boolean readOrWrite) throws GunChannelException {
        int v = readOrWrite
                ? observe.preReadClose(key) :
                observe.preWriteClose(key);
    }


    private DealResult invokeCloseEvent(SocketAddress key, boolean b) {
        dealCloseEvent(key, b);
        return DealResult.CLOSED;
    }


    private void sendMessage(byte[] src, SocketChannel channel) throws IOException {
        if (src != null && src.length > 0) {
            if (channel.isOpen()) {
                channel.write(ByteBuffer.wrap(src));
            } else {
                throw new IOException("socket close" + ":" + channel.getRemoteAddress());
            }
        }
    }


    private DealResult doOutputFilter(GunOutboundChecker filterDto, GunNettyChildChannel<SocketChannel> channel) {
        try {
            if (filterDto.isHasDataToOutput()) {
                sendMessage(filterDto.translate().source(), channel.channel());
            }
            return DealResult.NEXT;
        } catch (IOException exp) {
            channel.closeAndRemove(true);
            return DealResult.CLOSED;
        }
    }

    @Override
    public DealResult doInputFilter(GunInboundChecker filterDto) throws GunChannelException {
        final GunNettyChildChannel<SocketChannel> channel = filterDto.channel();
        if (channel.isValid()) {
            try {
                GunFunctionMapping<SocketChannel, byte[]> reader = GunBytesUtil::readFromChannel;
                operatorSrc.set(filterDto, reader.readBytes(channel.channel()));
                channel.recoverReadInterest();
            } catch (IOException | ReflectiveOperationException e) {
                channel.closeAndRemove(false);
                return invokeCloseEvent(channel.remoteAddress(), true);
            }
            return DealResult.NEXT;
        } else {
            return DealResult.NOT_DEAL_ALL_NEXT;
        }
    }

    @Override
    public DealResult doOutputFilter(GunOutboundChecker filterDto) throws GunChannelException {
        GunNettyChildChannel<SocketChannel> channel = filterDto.channel();
        try {
            return doOutputFilter(filterDto, channel);
        } catch (GunChannelException e) {
            return invokeCloseEvent(filterDto.channel().remoteAddress(), false);
        }
    }

    @Override
    public DealResult doConnFilter(GunNettyChildChannel<SocketChannel> ch) {
        return DealResult.NEXT;
    }
}