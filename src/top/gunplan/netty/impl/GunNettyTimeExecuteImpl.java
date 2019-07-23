package top.gunplan.netty.impl;

import top.gunplan.netty.AbstractGunTimeExecute;
import top.gunplan.netty.GunNettyTimer;

import java.nio.channels.SelectionKey;
import java.util.Set;


/**
 * GunNettyTimeExecuteImpl
 *
 * @author dosdrtt
 */
public final class GunNettyTimeExecuteImpl extends AbstractGunTimeExecute implements GunNettyManagerGetter<Void> {
    private volatile GunNettyCoreThreadManager manager;

    @Override
    public void run() {
        sum.increment();
        final Set<SelectionKey> keys = manager.availableChannel(sum.longValue());
        works.parallelStream().forEach(k -> {
            if (k.ifKeyEmptyExec() || keys.size() != 0) {
                new GunTimeWorkFunc(keys, sum.longValue()).execute(k);
            }
        });

    }

    @Override
    public Void registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return null;
    }

    /**
     * GunTimeWorkFunc
     *
     * @author dosdrtt
     */
    public static class GunTimeWorkFunc {
        private Set<SelectionKey> keys;
        private long nowTime;

        GunTimeWorkFunc(Set<SelectionKey> keys, long nowTime) {
            this.nowTime = nowTime;
            this.keys = keys;
        }

        void execute(GunNettyTimer w) {
            if (nowTime % w.interval() == 0) {
                w.doWork(keys);
            }
        }
    }


}
