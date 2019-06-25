package top.gunplan.netty.impl;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelTransfer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-19 00:02
 */

public class GunNettyChannelTransfer {
    private long value;
    private SocketChannel channel;

    public GunNettyChannelTransfer() {
    }

    public GunNettyChannelTransfer(SocketChannel channel) {
        this.channel = channel;
    }

    public GunNettyChannelTransfer(long value, SocketChannel channel) {
        this.value = value;
        this.channel = channel;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
