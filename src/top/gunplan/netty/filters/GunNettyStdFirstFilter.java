package top.gunplan.netty.filters;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyPropertyManager;
import top.gunplan.netty.impl.CunCoreDataEventLoop;
import top.gunplan.netty.impl.GunRequestFilterDto;
import top.gunplan.netty.impl.example.GunOutputFilterDto;
import top.gunplan.utils.AbstractGunBaseLogUtil;
import top.gunplan.utils.GunBytesUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * GunNettyStdFirstFilter First Input Filter and Last Output Filter
 *
 * @author dosdrtt
 */
@GunNetFilterOrder(index = 0)
public class GunNettyStdFirstFilter implements GunNettyFilter {

    private void dealCloseEvent(SelectionKey key) throws IOException {
        AbstractGunBaseLogUtil.debug("Client closed", "[CONNECTION]");
        key.channel().close();
        key.cancel();
    }

    @Override
    public DealResult doInputFilter(GunRequestFilterDto filterDto) throws Exception {
        byte[] readbata;
        SelectionKey key = filterDto.getKey();

        if (key.isValid()) {
            try {
                readbata = GunBytesUtil.readFromChannel((SocketChannel) key.channel(), GunNettyPropertyManager.getCore().getFileReadBufferMin());
                filterDto.setSrc(readbata);
            } catch (IOException e) {
                dealCloseEvent(key);
                return DealResult.CLOSE;
            }
            if (readbata == null) {
                dealCloseEvent(key);
                return DealResult.CLOSE;
            } else {
                key.interestOps(SelectionKey.OP_READ);
                ((CunCoreDataEventLoop) key.attachment()).listionSize.incrementAndGet();
                ((CunCoreDataEventLoop) key.attachment()).continueLoop();

                return DealResult.NEXT;

            }
        } else {
            return DealResult.NOTDEALALLNEXT;
        }
    }


    @Override
    public DealResult doOutputFilter(GunOutputFilterDto filterDto) throws IOException {
        SocketChannel channel = (SocketChannel) filterDto.getKey().channel();
        String s = new String(filterDto.getRespobj().serialize());
        channel.write(ByteBuffer.wrap(filterDto.getRespobj().serialize()));
        return DealResult.NEXT;
    }
}
