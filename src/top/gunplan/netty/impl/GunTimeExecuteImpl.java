package top.gunplan.netty.impl;

import top.gunplan.netty.AbstractGunTimeExecute;
import top.gunplan.netty.GunTimer;

import java.nio.channels.SelectionKey;
import java.util.Set;


/**
 * @author dosdrtt
 */
public final class GunTimeExecuteImpl extends AbstractGunTimeExecute {
    @Override
    public void run() {
        Thread.currentThread().setName(getClass().getName());
        sum.increment();
        final Set<SelectionKey> keys = CoreThreadManage.getAllofAvaliableClannel(sum.longValue());
        works.parallelStream().forEach(k -> {
            if (k.ifKeyEmptyExec() || keys.size() != 0) {
                new GunTimeWorkFunc(keys, sum.longValue()).execute(k);
            }
        });
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

        void execute(GunTimer w) {
            if (nowTime % w.interval() == 0) {
                w.doWork(keys);
            }
        }
    }


}
