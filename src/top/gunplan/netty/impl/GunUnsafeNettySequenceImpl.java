package top.gunplan.netty.impl;

class GunUnsafeNettySequenceImpl implements GunNettySequencer {
    private int val = 0;


    @Override
    public long nextSequence() {
        return val++ & Long.MAX_VALUE;
    }
}
