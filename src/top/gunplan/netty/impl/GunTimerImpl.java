package top.gunplan.netty.impl;

import top.gunplan.netty.GunTimer;
import top.gunplan.netty.anno.GunTimeAnno;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;
import java.util.Set;

/**
 * this is timer std implements
 *
 * @author dosdrtt
 */
public class GunTimerImpl implements GunTimer {

    @Override
    public int interval() {
        return -1;
    }

    @Override
    public int runingTimes() {
        return 0;
    }

    @Override
    public int doWork(Set<SelectionKey> keys) {
        return 0;
    }
}
