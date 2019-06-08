package top.gunplan.netty.impl.buffer;


import top.gunplan.netty.GunBufferManage;

/**
 *
 */
public class GunNettySafedBufferManager extends BaseBufferManager {




    private byte[] malloc(int size, int len) throws Exception {
        return new byte[size * len];
    }

    @Override
    public GunNettyBufferStream getBuffer(int size) {
        return null;
    }

}
