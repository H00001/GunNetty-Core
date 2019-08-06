/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import java.util.concurrent.atomic.AtomicLong;

/**
 * GunSafeNettySequenceImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-06 15:20
 */
final class GunSafeNettySequenceImpl implements GunNettySequencer {
    private AtomicLong adder = new AtomicLong();

    @Override
    public long nextSequence() {
        return adder.incrementAndGet();

    }

    @Override
    public long lastSequence() {
        return adder.decrementAndGet();
    }
}
