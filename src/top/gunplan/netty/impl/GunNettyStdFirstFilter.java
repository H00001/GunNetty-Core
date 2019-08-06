/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunFunctionMappingInterFace;
import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.utils.GunBytesUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
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
    public boolean doConnFilter(Channel ch) {
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
        final SelectionKey key = filterDto.getKey();
        final SocketChannel channel = (SocketChannel) key.channel();
        if (key.isValid()) {
            try {
                GunFunctionMappingInterFace<SocketChannel, byte[]> reader = GunBytesUtil::readFromChannel;
                filterDto.setSource(reader.readBytes(channel));
            } catch (IOException e) {
                return invokeCloseEvent(key, true);
            }
            key.interestOps(SelectionKey.OP_READ);
            ((GunDataEventLoop) key.attachment()).incrAndContinueLoop();
            return DealResult.NEXT;
        } else {
            return DealResult.NOTDEALALLNEXT;
        }

    }

    private DealResult invokeCloseEvent(SelectionKey key, boolean b) {
        if (!b) {
            //when write stop happened
            //if it is conn event it doesn't have attachment
            if (key.attachment() != null && key.attachment() instanceof GunDataEventLoop) {
                ((GunDataEventLoop) key.attachment()).decreaseAndStop();
            }
        }
        dealCloseEvent(key, b);
        return DealResult.CLOSE;
    }

    @Override
    public DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws GunChannelException {
        SocketChannel channel = (SocketChannel) filterDto.getKey().channel();
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
    public DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto, SocketChannel channel) {
        try {
            filterDto.translate();
            sendMessage(filterDto.source(), channel);
            return DealResult.NEXT;
        } catch (IOException exp) {
            throw new GunChannelException(exp);
        }
    }
}