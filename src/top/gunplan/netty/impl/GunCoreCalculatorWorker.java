package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.protocol.GunNetOutputInterface;

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
    public void run() {
        final GunNettyInputFilterChecker gunFilterObj = new GunNettyInputFilterChecker(key);
        for (GunNettyFilter filter : this.pipeline.getFilters()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filter.doInputFilter(gunFilterObj);
            } catch (Exception e) {
                this.pipeline.getHandel().dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NATALINA) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {

                return;
            } else if (result == GunNettyFilter.DealResult.NOTDEALALLNEXT) {
                return;
            }
        }
        GunNetOutputInterface respObject = null;
        try {
            respObject = this.pipeline.getHandel().dealDataEvent(gunFilterObj.getObject());
        } catch (Exception e) {
            this.pipeline.getHandel().dealExceptionEvent(e);
        }
        GunNettyOutputFilterChecker responseFilterDto = new GunNettyOutputFilterChecker(respObject);
        responseFilterDto.setKey(gunFilterObj.getKey());
        ListIterator<GunNettyFilter> filters = pipeline.getFilters().listIterator(pipeline.getFilters().size());

        while (filters.hasPrevious()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filters.previous().doOutputFilter(responseFilterDto);
            } catch (Exception e) {
                this.pipeline.getHandel().dealExceptionEvent(e);
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

    }
}
