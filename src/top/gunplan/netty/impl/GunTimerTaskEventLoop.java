package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunTimer;
import top.gunplan.netty.anno.GunTimeAnno;

import java.lang.reflect.Method;
import java.nio.channels.SelectionKey;

/**
 * todo
 * GunTimerTaskEventLoop
 * @author dosdrtt
 */
public class GunTimerTaskEventLoop implements GunCoreEventLoop {
    private final GunTimer timer;
    private final long ci = 0;

    /**
     * @param timer ,GunTimer
     */
    public GunTimerTaskEventLoop(GunTimer timer) {
        this.timer = timer;
    }

    @Override
    public void dealEvent(SelectionKey key) throws Exception {
        //todo
        Method[] mds = timer.getClass().getMethods();
        int usefulmdcon = 0;
        for (int i = 0; i < mds.length; i++) {
            GunTimeAnno anno = mds[i].getAnnotation(GunTimeAnno.class);
            if (anno != null) {
                usefulmdcon = i;
            }
        }
    }

    @Override
    public void run() {

    }
}
