package top.gunplan.netty.impl.buffer;


import top.gunplan.netty.GunBufferManage;
import top.gunplan.netty.GunLinkedData;

/**
 *
 */
public class GunSafedPulledBuffer implements GunBufferManage {
    private int capital = 20;
    private GunPulledLinkedData[] pulllist = new GunPulledLinkedData[capital];
    private int[] size = {10, 20, 40, 40, 80, 80, 160, 180, 200, 240, 280, 320, 400, 480, 560, 1024, 1024, 1024, 1024, 1024};


    public void init() throws Exception {
        for (int i = 0; i < capital; i++) {
            byte[] tru = malloc(size[i], 1);
            pulllist[i] = new GunPulledLinkedData(tru);
        }
    }

    @Override
    public void addEvent() {

    }

    @Override
    public void descEvent() {

    }

    private byte[] malloc(int size, int len) throws Exception {
        return new byte[size * len];
    }

    @Override
    public GunLinkedData malloc(int len) throws Exception {
        for (int i = 0; i < capital; i++) {
            if (size[i] > len && pulllist[i].getTime() == 0) {
                pulllist[i].addLink();
                if (pulllist[i].getTime() == 1) {
                    return pulllist[i];
                } else {
                    return malloc(len);
                }
            }
        }
        return new GunUnPulledLinkedData(malloc(len, 1));

    }
}
