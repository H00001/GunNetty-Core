/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

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
        return new GunUnsafeNettySequenceImpl();
    }

    /**
     * new newThreadSafeSequencer
     *
     * @return GunNettySequencer
     */
    static GunNettySequencer newThreadSafeSequencer() {
        return new GunSafeNettySequenceImpl();
    }

    long nextSequence();

    long lastSequence();


    default int nextSequenceInt32() {
        return (int) nextSequence();
    }

    default int nextSequenceInt32WithLimit(int limit) {
        return (int) (nextSequence() & limit);
    }

}
