/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;

import java.nio.channels.SelectionKey;
import java.util.Set;


/**
 * GunNettyTimeExecuteImpl
 *
 * @author dosdrtt
 */
final class GunNettyTimeExecuteImpl extends AbstractGunTimeExecutor {
    private int var;

    public GunNettyTimeExecuteImpl(int var) {
        this.var = var;
    }

    @Override
    public boolean loop() {
        final Set<SelectionKey> keys = manager.availableChannel(sequencer.nextSequenceInt32WithLimit(var));
        works.parallelStream().forEach(k -> {
            if (k.ifKeyEmptyExec() || keys.size() != 0) {
                new GunTimeWorkFunc(keys, sum.longValue()).execute(k);
            }
        });
        return false;

    }

    @Override
    public void shutdown() {

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
