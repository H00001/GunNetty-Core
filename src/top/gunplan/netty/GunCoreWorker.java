package top.gunplan.netty;


import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.nio.utils.GunBytesUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;


/**
 *
 * @author dosdrtt
 */

public final class GunCoreWorker extends GunBootServer.BaseGunNettyWorker implements Runnable {

    private final List<GunNettyFilter> filters;

    public GunCoreWorker(final List<GunNettyFilter> filters, final GunBootServer.GunNetHandle dealHanders, final SocketChannel channel) {
        super(dealHanders, channel);
        this.filters = filters;


    }
    @Override
    public synchronized void run() {
        byte[] readbata = null;
        try {
            readbata = GunBytesUtil.readFromChannel(channel, 1024);
            if (readbata == null) {
                this.handel.dealCloseEvent();
                channel.close();
            }
        } catch (Exception e) {
            this.handel.dealExceptionEvent(e);
        }
        if (readbata != null)
        {
            final GunRequestFilterDto gunFilterObj = new GunRequestFilterDto(readbata);
            this.filters.forEach(netty -> netty.doRequestFilter(gunFilterObj));
            GunNetResponseInterface respObject = null;
            try {
                respObject = this.handel.dealDataEvent(gunFilterObj.getObject());
            } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                this.handel.dealExceptionEvent(e);
            }
            GunResponseFilterDto responseFilterDto = new GunResponseFilterDto(respObject);
            this.filters.forEach(netty -> netty.doResponseFilter(responseFilterDto));

            if (responseFilterDto.getRespobj().isReturn()) {
                try {
                    super.channel.write(ByteBuffer.wrap(responseFilterDto.getRespobj().serialize()));
                } catch (IOException e) {
                    this.handel.dealExceptionEvent(e);
                }

            }
        }
    }
}
