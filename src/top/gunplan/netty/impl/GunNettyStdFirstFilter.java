/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunFunctionMappingInterFace;
import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.utils.GunBytesUtil;

import java.io.IOException;
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
public final class GunNettyStdFirstFilter implements GunNettyFilter {

    private final GunNettyBaseObserve observe;

    public GunNettyStdFirstFilter(GunNettyBaseObserve baseObserve) {
        this.observe = baseObserve;
    }


    @Override
    public boolean doConnFilter(GunNettyChannel ch) {
        return true;
    }

    private void dealCloseEvent(SelectionKey key, boolean readOrWrite) throws GunChannelException {
        final SocketChannel channel = (SocketChannel) key.channel();
        final Selector selector = key.selector();
        try {
            int v = readOrWrite
                    ? observe.preReadClose(channel.getRemoteAddress()) :
                    observe.preWriteClose(channel.getRemoteAddress());
            channel.close();
            selector.wakeup();
            selector.select(v);
        } catch (IOException e) {
            throw new GunChannelException(e);
        }

    }


    @Override
    public DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws GunChannelException {
        final GunNettyChildChannel<SocketChannel> channel = filterDto.getKey();
        if (channel.isValid()) {
            try {
                GunFunctionMappingInterFace<SocketChannel, byte[]> reader = GunBytesUtil::readFromChannel;
                filterDto.setSource(reader.readBytes(channel));
            } catch (IOException e) {
                return invokeCloseEvent(key, true);
            }
            channel.addReadObserve();
            channel.continueLoop();
            return DealResult.NEXT;
        } else {
            return DealResult.NOT_DEAL_ALL_NEXT;
        }

    }

    private DealResult invokeCloseEvent(SelectionKey key, boolean b) {
        dealCloseEvent(key, b);
        return b ? DealResult.CLOSED_WHEN_READ : DealResult.CLOSE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws GunChannelException {
        GunNettyChildChannel<SocketChannel> channel = ((GunNettyChannel<SocketChannel>) filterDto.getKey().attachment());
        try {
            return doOutputFilter(filterDto, channel);
        } catch (GunChannelException e) {
            return invokeCloseEvent(filterDto.getKey(), false);
        }
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

    @Override
    public DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto, GunNettyChildChannel<SocketChannel> channel) {
        try {
            channel.channel().getRemoteAddress();
            filterDto.translate();
            sendMessage(filterDto.source(), channel.channel());
            return DealResult.NEXT;
        } catch (IOException exp) {
            throw new GunChannelException(exp);
        }
    }
}