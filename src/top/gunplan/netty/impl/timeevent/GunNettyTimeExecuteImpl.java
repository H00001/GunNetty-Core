/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;


/**
 * GunNettyTimeExecuteImpl
 *
 * @author dosdrtt
 * @deprecated
 */
@Deprecated
final class GunNettyTimeExecuteImpl extends AbstractGunTimeExecutor {

    GunNettyTimeExecuteImpl() {

    }

    @Override
    public boolean loop() {
        works.parallelStream().forEach(k -> {

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

        private long nowTime;

        GunTimeWorkFunc(long nowTime) {
            this.nowTime = nowTime;
        }

        void execute(GunNettyTimer w) {

        }
    }


}
