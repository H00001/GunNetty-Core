package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetResponseInterface;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;


/**
 * @author dosdrtt
 */

public final class GunCoreCalculatorWorker extends GunBootServer.BaseGunNettyWorker implements Runnable {

    private final List<GunNettyFilter> filters;
    private final byte[] data;

    public GunCoreCalculatorWorker(final List<GunNettyFilter> filters, final GunBootServer.GunNetHandle dealHanders, final SocketChannel channel, byte[] data) {
        super(dealHanders, channel);
        this.filters = filters;
        this.data = data;
    }

    @Override
    public synchronized void run() {
        final GunRequestFilterDto gunFilterObj = new GunRequestFilterDto(data);
        for (GunNettyFilter filter : this.filters) {
            if (!filter.doRequestFilter(gunFilterObj)) {
                break;
            }
        }
        GunNetResponseInterface respObject = null;
        try {
            respObject = this.handel.dealDataEvent(gunFilterObj.getObject());
        } catch (Exception e) {
            this.handel.dealExceptionEvent(e);
        }
        GunResponseFilterDto responseFilterDto = new GunResponseFilterDto(respObject);
        for (GunNettyFilter filter : this.filters) {
            if (!filter.doResponseFilter(responseFilterDto)) {
                break;
            }
        }

        if (responseFilterDto.getRespobj().isReturn()) {
            try {
                super.channel.write(ByteBuffer.wrap(responseFilterDto.getRespobj().serialize()));
            } catch (IOException e) {
                this.handel.dealExceptionEvent(e);
            }

        }
    }
}
