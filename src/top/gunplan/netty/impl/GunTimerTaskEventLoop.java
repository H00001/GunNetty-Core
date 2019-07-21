package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunTimeAnno;

import java.lang.reflect.Method;
import java.nio.channels.SelectionKey;
import java.util.Set;

/**
 * todo
 * GunTimerTaskEventLoop
 * @author dosdrtt
 */
public class GunTimerTaskEventLoop implements GunCoreEventLoop {
    private final GunNettyTimer timer;
    private final long ci = 0;

    /**
     * @param timer ,GunNettyTimer
     */
    public GunTimerTaskEventLoop(GunNettyTimer timer) {
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

    @Override
    public <V extends GunCoreEventLoop> V registerManager(GunNettyCoreThreadManager manager) {
        return null;
    }

    @Override
    public Set<SelectionKey> availableSelectionKey() {
        return null;
    }

    @Override
    public void startEventLoop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void stopEventLoop() {

    }
}
