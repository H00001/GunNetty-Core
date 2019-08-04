package top.gunplan.netty.impl;

/**
 * GunNettySequencer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-05 00:22
 */

@FunctionalInterface
public interface GunNettySequencer {
    long nextSequence();


    default int nextSequenceInt32() {
        return (int) nextSequence();
    }

    default int nextSequenceInt32WithLimit(int limit) {
        return (int) (nextSequence() & limit);
    }

}
