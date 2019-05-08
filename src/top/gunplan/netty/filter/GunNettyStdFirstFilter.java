package top.gunplan.netty.filter;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunFunctionMappingInterFace;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyPropertyManagerImpl;
import top.gunplan.netty.impl.GunCoreDataEventLoop;
import top.gunplan.netty.impl.GunInputFilterChecker;
import top.gunplan.netty.impl.GunOutputFilterChecker;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;
import top.gunplan.utils.GunBytesUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
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

    public GunNettyStdFirstFilter() {
        coreProperty = GunNettyPropertyManagerImpl.getProperty("core");
    }


    private void dealCloseEvent(SelectionKey key) throws IOException {
        AbstractGunBaseLogUtil.debug("Client closed", "[CONNECTION]");
        key.channel().close();
        key.cancel();
    }

    private GunCoreProperty coreProperty;

    @Override
    public DealResult doInputFilter(GunInputFilterChecker filterDto) throws Exception {

        byte[] readbata;
        SelectionKey key = filterDto.getKey();

        if (key.isValid()) {
            try {
                GunFunctionMappingInterFace<SocketChannel, byte[]> reader = GunBytesUtil::readFromChannel;
                readbata = reader.readBytes((SocketChannel) key.channel());
                filterDto.setSrc(readbata);
            } catch (IOException e) {
                dealCloseEvent(key);
                e.printStackTrace();
                return DealResult.CLOSE;
            }
            if (readbata == null) {
                dealCloseEvent(key);
                return DealResult.CLOSE;
            } else {
                if (coreProperty.getConnection() == GunCoreProperty.connectionType.CLOSE) {
                    filterDto.getKey().cancel();
                } else if (coreProperty.getConnection() == GunCoreProperty.connectionType.KEEP_ALIVE) {
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
    public DealResult doOutputFilter(GunOutputFilterChecker filterDto) throws IOException {
        SocketChannel channel = (SocketChannel) filterDto.getKey().channel();
        if (filterDto.getRespobj() != null) {
            channel.write(ByteBuffer.wrap(filterDto.getRespobj().serialize()));
        }
        if (coreProperty.getConnection() == GunCoreProperty.connectionType.CLOSE) {
            filterDto.getKey().channel().close();
            AbstractGunBaseLogUtil.debug("close initiative");
        }
        return DealResult.NEXT;
    }
}
