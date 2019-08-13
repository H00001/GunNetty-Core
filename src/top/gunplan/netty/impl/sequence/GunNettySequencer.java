/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.sequence;

import top.gunplan.utils.GunNumberUtil;

/**
 * GunNettySequencer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-05 00:22
 */

public interface GunNettySequencer {
    /**
     * new ThreadUnSafeSequencer
     *
     * @return GunNettySequencer
     */
    static GunNettySequencer newThreadUnSafeSequencer() {
        return new GunNettyUnsafeSequenceImpl();
    }

    /**
     * new newThreadSafeSequencer
     *
     * @return GunNettySequencer
     */
    static GunNettySequencer newThreadSafeSequencer() {
        return new GunNettySafeSequenceImpl();
    }

    /**
     * next sequence with long type
     *
     * @return seq
     */
    long nextSequence();

    /**
     * last sequence with long type
     *
     * @return seq
     */
    long lastSequence();


    /**
     * next sequence with int type
     *
     * @return sequence
     */
    default int nextSequenceInt32() {
        return (int) nextSequence();
    }


    /**
     * next sequence
     *
     * @param limit max sequence
     * @return sequence
     */
    default int nextSequenceInt32WithLimit(int limit) {
        return GunNumberUtil.isPowOf2(limit) ? ((int) (nextSequence() & (limit - 1))) :
                ((int) (nextSequence() % limit));

    }

}
