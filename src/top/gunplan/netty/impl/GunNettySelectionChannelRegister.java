package top.gunplan.netty.impl;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

/**
 * GunNettySelectionChannelRegister
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-20 23:18
 */

@FunctionalInterface
public interface GunNettySelectionChannelRegister<Channel extends SelectableChannel> {
    SelectionKey registerReadKey(Channel channel) throws IOException;
}
