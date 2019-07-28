package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyInputFilterChecker;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SelectionKey;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author dosdrtt
 */

public final class GunCoreCalculatorWorker extends BaseGunNettyWorker {
    private final AtomicInteger waitSize;
    private final SelectionKey key;


    GunCoreCalculatorWorker(final GunNettyPipeline pipeline
            , final SelectionKey key, AtomicInteger waitSize) {
        super(pipeline);
        this.key = key;
        this.waitSize = waitSize;
    }


    @Override
    public void work() {
        final GunNettyInputFilterChecker gunFilterObj = new GunNettyInputFilterChecker(key);
        for (GunNettyFilter filter : this.pipeline.filters()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filter.doInputFilter(gunFilterObj);
            } catch (GunChannelException e) {
                this.pipeline.handel().dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NATALINA) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {

                return;
            } else if (result == GunNettyFilter.DealResult.NOTDEALALLNEXT) {
                return;
            }
        }
        GunNetOutbound output = null;
        try {
            output = this.pipeline.handel().dealDataEvent(gunFilterObj.getTransfer());
        } catch (GunChannelException e) {
            this.pipeline.handel().dealExceptionEvent(e);
        }
        GunNettyOutputFilterChecker responseFilterDto = new GunNettyOutputFilterChecker(output);
        responseFilterDto.setKey(gunFilterObj.getKey());
        ListIterator<GunNettyFilter> filters = pipeline.filters().listIterator(pipeline.filters().size());

        while (filters.hasPrevious()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filters.previous().doOutputFilter(responseFilterDto);
            } catch (GunChannelException e) {
                this.pipeline.handel().dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOTDEALOUTPUT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {
                waitSize.decrementAndGet();
                return;
            } else if (result == GunNettyFilter.DealResult.NOTDEALALLNEXT) {
                return;
            }

        }
        destroy();

    }
}
