package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunPilelineInterface;
import top.gunplan.netty.impl.example.GunOutputFilterDto;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author dosdrtt
 */

public final class GunCoreCalculatorWorker extends BaseGunNettyWorker {


    private final AtomicInteger waitSize;
    private SelectionKey key;

    GunCoreCalculatorWorker(final GunPilelineInterface pileline, final SelectionKey channel, AtomicInteger waitSize) {
        super(pileline);
        this.key = channel;
        this.waitSize = waitSize;
    }

    @Override
    public synchronized void run() {
        final GunRequestFilterDto gunFilterObj = new GunRequestFilterDto(key);
        for (GunNettyFilter filter : this.pileline.getFilters()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filter.doInputFilter(gunFilterObj);
            } catch (Exception e) {
                this.pileline.getHandel().dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOTDEALINPUT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {

                return;
            } else if (result == GunNettyFilter.DealResult.NOTDEALALLNEXT) {
                return;
            }
        }
        GunNetResponseInterface respObject = null;
        try {
            respObject = this.pileline.getHandel().dealDataEvent(gunFilterObj.getObject());
        } catch (Exception e) {
            this.pileline.getHandel().dealExceptionEvent(e);
        }
        GunOutputFilterDto responseFilterDto = new GunOutputFilterDto(respObject);
        responseFilterDto.setKey(gunFilterObj.getKey());
        ListIterator<GunNettyFilter> filters = pileline.getFilters().listIterator(pileline.getFilters().size());

        while (filters.hasPrevious()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = filters.previous().doOutputFilter(responseFilterDto);
            } catch (Exception e) {
                this.pileline.getHandel().dealExceptionEvent(e);
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
