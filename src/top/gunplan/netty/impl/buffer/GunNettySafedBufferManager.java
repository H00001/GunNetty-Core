package top.gunplan.netty.impl.buffer;


/**
 * GunNettySafedBufferManager
 *
 * @author frank albert
 * @date 2019-06-08 19:11
 *
 * @version 0.0.0.1
 */
public class GunNettySafedBufferManager extends BaseBufferManager {


    public GunNettySafedBufferManager(boolean sstrategy) {
        super(sstrategy);
    }


    @Override
    public GunNettyBufferStream getBuffer(int size) {
        return new GunNettySafeBuffer(size);
    }

}
