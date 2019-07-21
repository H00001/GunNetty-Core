package top.gunplan.netty.impl;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunFunctionMappingInterFace;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.GunBytesUtil;
import top.gunplan.utils.GunLogger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * GunNettyStdFirstFilter First Input Filter and Last Output Filter
 * this class is high dangerous
 *
 * @author dosdrtt
 */
@GunNetFilterOrder
public class GunNettyStdFirstFilter implements GunNettyFilter {
    private static final GunLogger LOG = GunNettyContext.logger.setTAG(GunNettyStdFirstFilter.class);
    private GunNettyCoreProperty coreProperty;

    public GunNettyStdFirstFilter() {
        coreProperty = GunNettyPropertyManagerImpl.coreProperty();
    }


    @Override
    public boolean doConnFilter(Channel ch) {
        return true;
    }

    private void dealCloseEvent(SelectionKey key) throws GunChannelException {
        LOG.debug("Client closed", "[CONNECTION]");
        try {
            key.channel().close();
            key.selector().wakeup();
            key.selector().selectNow();
        } catch (IOException e) {
            throw new GunChannelException(e);
        }

    }


    @Override
    public DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws GunChannelException {

        byte[] data;
        SelectionKey key = filterDto.getKey();

        if (key.isValid()) {
            try {
                GunFunctionMappingInterFace<SocketChannel, byte[]> reader = GunBytesUtil::readFromChannel;
                data = reader.readBytes((SocketChannel) key.channel());
                filterDto.setSource(data);
            } catch (IOException e) {
                dealCloseEvent(key);
                LOG.error(e);
                return DealResult.CLOSE;
            }
            if (data == null) {
                dealCloseEvent(key);
                return DealResult.CLOSE;
            } else {
                if (coreProperty.getConnection() == GunNettyCoreProperty.connectionType.CLOSE) {
                    filterDto.getKey().cancel();
                } else if (coreProperty.getConnection() == GunNettyCoreProperty.connectionType.KEEP_ALIVE) {
                    key.interestOps(SelectionKey.OP_READ);
                    ((GunCoreDataEventLoop) key.attachment()).incrAndContinueLoop();

                }
                return DealResult.NEXT;

            }
        } else {
            return DealResult.NOTDEALALLNEXT;
        }
    }

    @Override
    public DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws GunChannelException {
        SocketChannel channel = (SocketChannel) filterDto.getKey().channel();
        return doOutputFilter(filterDto, channel);
    }

    private void sendMessage(byte[] src, SocketChannel channel) throws IOException {
        if (src != null && src.length > 0) {
            if (channel.isOpen()) {
                channel.write(ByteBuffer.wrap(src));
            } else {
                throw new IOException("socket close" + ":" + channel.getRemoteAddress());
            }
        }
    }

    @Override
    public DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto, SocketChannel channel) {
        try {
            filterDto.translate();
            sendMessage(filterDto.source(), channel);
            return DealResult.NEXT;
        } catch (IOException exp) {
            LOG.error(exp);
            return DealResult.CLOSE;
        }
    }
}
