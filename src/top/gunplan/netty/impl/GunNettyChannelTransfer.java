package top.gunplan.netty.impl;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelTransfer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-17 07:39
 */
public interface GunNettyChannelTransfer<U extends Channel> {
    U getChannel();

    void setChannel(U channel);

    void setValue(long value);
}
