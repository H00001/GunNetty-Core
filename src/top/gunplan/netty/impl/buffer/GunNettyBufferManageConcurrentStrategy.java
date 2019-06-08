package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Objects;
import java.util.Queue;

/**
 * GunNettyBufferManageConcurrentStrategy
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 16:06
 */
public class GunNettyBufferManageConcurrentStrategy implements GunNettyBufferManageStrategy {
    @Override
    public void onRelease(GunNettyBufferStream stream, Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using) {
        stream.flushPoint();
        stream.flushData();
        using.remove(stream);
        operator.offer(new SoftReference<>(stream));
    }

    @Override
    public GunNettyBufferStream onNeed(Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using, int size) {
        GunNettyBufferStream stream;
        GunNettyBufferStream head = operator.element().get();
        for (; ; ) {
            stream = Objects.requireNonNull(operator.poll()).get();
            if (head == stream) {
                break;
            }
            assert stream != null;
            if (stream.maxLen() < size + 5 && stream.maxLen() > size - 5) {
                using.offer(stream);
                return stream;
            } else {
                operator.offer(new SoftReference<>(stream));
            }
        }
        return null;
    }
}
