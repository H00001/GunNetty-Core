package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * GunNettyBufferManageConcurrentStrategy
 *
 * @author frank albert
 * @version 0.0.0.1
 */
class GunNettyBufferManageConcurrentStrategy extends BaseGunNettyBufferManageStrategy {
    @Override
    public void onRelease(GunNettyBufferStream stream, Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using) {
        super.onRelease(stream, operator, using);
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
            if (stream.maxLen() < size + 10 && stream.maxLen() > size) {
                using.offer(stream);
                return stream;
            } else {
                operator.offer(new SoftReference<>(stream));
            }
        }
        return null;
    }

    @Override
    public Queue<SoftReference<GunNettyBufferStream>> acquireSoftQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Override
    public Queue<GunNettyBufferStream> acquireStrongQueue() {
        return new ConcurrentLinkedQueue<>();
    }
}
