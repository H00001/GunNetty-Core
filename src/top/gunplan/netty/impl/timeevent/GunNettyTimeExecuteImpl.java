/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunTimeExecutor;

import java.util.Arrays;


/**
 * GunNettyTimeExecuteImpl
 *
 * @author dosdrtt
 */
final class GunNettyTimeExecuteImpl extends AbstractGunTimeExecutor {

    GunNettyTimeExecuteImpl() {

    }

    @Override
    public boolean loop() {
        works.parallelStream().forEach(k -> {
            if (k.ifKeyEmptyExec()) {
                new GunTimeWorkFunc(seq.nextSequence()).execute(k);
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

        private long nowTime;

        GunTimeWorkFunc(long nowTime) {
            this.nowTime = nowTime;
        }

        void execute(GunNettyTimer w) {
            Arrays.stream(w.getClass().getMethods()).
                    filter(who -> {
                        final GunTimeExecutor g = who.getAnnotation(GunTimeExecutor.class);
                        return g != null && nowTime % g.interval() == 0;
                    })
                    .forEach(who -> {
                        try {
                            who.invoke(w);
                        } catch (ReflectiveOperationException e) {
                            w.timeExecuteError(who.getName(), e);
                        }
                    });
        }
    }


}
