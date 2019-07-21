package top.gunplan.netty.impl;

import com.lmax.disruptor.EventFactory;
import top.gunplan.netty.impl.eventloop.GunNettyChannelTransferImpl;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelEventFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-18 23:55
 */
public class GunNettyChannelEventFactory implements EventFactory<GunNettyChannelTransfer<SocketChannel>> {
    public GunNettyChannelEventFactory() {

    }

    @Override
    public GunNettyChannelTransfer<SocketChannel> newInstance() {
        return new GunNettyChannelTransferImpl(0, null);
    }
}
