/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ListIterator;

/**
 * GunAcceptWorker
 *
 * @author dosdrtt
 * @date 2019-04-25
 */
public final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
    private final SocketChannel channel;

    GunAcceptWorker(final GunNettyPipeline l, final SocketChannel channel) {
        super(l, null);
        this.channel = channel;
    }


    @Override
    public void work() {
        for (GunNettyFilter filter : pipeline.filters()) {
            if (!filter.doConnFilter(channel)) {
                return;
            }
        }
        GunNetOutbound ob = null;
        try {
            ob = this.pipeline.handel().dealConnEvent(channel.getRemoteAddress());
        } catch (IOException e) {
            handle.dealExceptionEvent(new GunChannelException(e));
        }
        ListIterator<GunNettyFilter> filters = pipeline.filters().listIterator(pipeline.filters().size());
        while (filters.hasPrevious()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filters.previous().doOutputFilter(new GunNettyOutputFilterChecker(ob, null), channel);
            } catch (GunChannelException e) {
                handle.dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOTDEALOUTPUT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {
                return;
            } else if (result == GunNettyFilter.DealResult.NOTDEALALLNEXT) {
                return;
            }

        }


    }
}
