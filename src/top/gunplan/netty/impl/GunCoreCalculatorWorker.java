package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunPilelineInterface;
import top.gunplan.netty.impl.example.GunResponseFilterDto;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * @author dosdrtt
 */

public final class GunCoreCalculatorWorker extends BaseGunNettyWorker {
    private final byte[] data;

    GunCoreCalculatorWorker(final GunPilelineInterface pileline, final SocketChannel channel, byte[] data) {
        super(pileline, channel);
        this.data = data;
    }

    @Override
    public synchronized void run() {
        final GunRequestFilterDto gunFilterObj = new GunRequestFilterDto(data);
        for (GunNettyFilter filter : this.pileline.getFilters()) {
            if (!filter.doInputFilter(gunFilterObj)) {
                return;
            }
        }
        GunNetResponseInterface respObject = null;
        try {
            respObject = this.pileline.getHandel().dealDataEvent(gunFilterObj.getObject());
        } catch (Exception e) {
            this.pileline.getHandel().dealExceptionEvent(e);
        }
        GunResponseFilterDto responseFilterDto = new GunResponseFilterDto(respObject);
        for (GunNettyFilter filter : this.pileline.getFilters()) {
            if (!filter.doOutputFilter(responseFilterDto)) {
                return;
            }
        }

        if (responseFilterDto.getRespobj().isReturn()) {
            try {
                if (channel.isConnected()) {
                    super.channel.write(ByteBuffer.wrap(responseFilterDto.getRespobj().serialize()));
                }
            } catch (IOException e) {
                this.pileline.getHandel().dealExceptionEvent(e);
            }

        }
    }
}
