/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunFunctionMapping;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.filter.GunNettyConnFilter;
import top.gunplan.netty.filter.GunNettyDataFilter;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.impl.checker.AbstractGunChecker;
import top.gunplan.netty.impl.checker.GunInboundChecker;
import top.gunplan.netty.impl.checker.GunOutboundChecker;
import top.gunplan.netty.observe.DefaultGunBaseObserve;
import top.gunplan.netty.observe.GunNettyBaseObserve;
import top.gunplan.utils.GunBytesUtil;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * GunNettyStdFirstFilter First Input Filter and Last Output Filter
 * this class is high dangerous
 *
 * @author dosdrtt
 */
@GunNetFilterOrder
public final class GunNettyStdFirstFilter implements GunNettyDataFilter, GunNettyConnFilter {

    private volatile GunNettyBaseObserve observe = new DefaultGunBaseObserve();
    private static Field operatorSrc;

    static {
        try {
            operatorSrc = AbstractGunChecker.class.getDeclaredField("src");
            assert operatorSrc != null;
        } catch (NoSuchFieldException ignore) {

        }
        operatorSrc.setAccessible(true);

    }

    public GunNettyStdFirstFilter() {
    }


    public GunNettyStdFirstFilter setObserve(GunNettyBaseObserve observe) {
        this.observe = observe;
        return this;
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


    public static int readSendMessage(SocketChannel socketChannel,
                                      ByteBuffer sended) throws IOException {
        SelectionKey key = null;
        Selector writeSelector = null;
        int attempts = 0;
        int bytesProduced = 0;
        try {
            while (sended.hasRemaining()) {
                int len = socketChannel.write(sended);
                if (len < 0) {
                    throw new EOFException();
                }
                bytesProduced += len;
                //send fail
                if (len == 0) {
                    writeSelector = GunNettySelectorFactory.INSTANCE.allocate();
                    key = socketChannel.register(writeSelector, SelectionKey.OP_WRITE);
                    if (writeSelector.select(3000) == 0) {
                        if (attempts > 3) {
                            throw new IOException("Client disconnected");
                        }
                    } else {
                        attempts--;
                    }
                } else {
                    attempts = 0;
                }
            }
        } finally {
            if (key != null) {
                key.cancel();
                key = null;
                //help gc
            }
            if (writeSelector != null) {
                // Cancel the key.
                GunNettySelectorFactory.INSTANCE.release(writeSelector);
            }
        }
        return bytesProduced;
    }

    private void sendMessage(byte[] src, SocketChannel channel) throws IOException {
        if (src != null && src.length > 0) {
            if (channel.isOpen()) {
                ByteBuffer buffer = ByteBuffer.allocateDirect(src.length);
                buffer.put(src);
                buffer.flip();
                if (readSendMessage(channel, buffer) <= 0) {
                    throw new IOException("socket send error" + ":" + channel.getRemoteAddress());
                }
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
                channel.closeAndRemove(false).destroy();
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