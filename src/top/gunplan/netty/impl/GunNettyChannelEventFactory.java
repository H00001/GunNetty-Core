package top.gunplan.netty.impl;

import com.lmax.disruptor.EventFactory;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelEventFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-18 23:55
 */
public class GunNettyChannelEventFactory implements EventFactory<GunNettyChannelTransfer> {
    public GunNettyChannelEventFactory() {

    }

    @Override
    public GunNettyChannelTransfer newInstance() {
        return new GunNettyChannelTransfer(0, null);
    }
}
