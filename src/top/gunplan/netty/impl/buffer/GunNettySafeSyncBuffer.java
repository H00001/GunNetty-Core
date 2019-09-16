package top.gunplan.netty.impl.buffer;

/**
 * GunNettySafeSyncBuffer
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-09 18:42
 */
public class GunNettySafeSyncBuffer extends GunNettySafeBuffer {
    public GunNettySafeSyncBuffer(int len) {
        super(len);
    }

    @Override
    public synchronized void write(byte[] bin) {
        super.write(bin);
    }

    @Override
    public synchronized long readPoint() {
        return super.readPoint();
    }

    @Override
    public synchronized byte read() {
        return super.read();
    }

    @Override
    public synchronized void write(byte bin) {
        super.write(bin);
    }

    @Override
    public synchronized byte[] read(int len) {
        return super.read(len);
    }
}
