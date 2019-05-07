package top.gunplan.netty.impl;

import top.gunplan.netty.GunTimer;
import top.gunplan.netty.anno.GunTimeAnno;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;

public class GunTImerImpl implements GunTimer {
    @GunTimeAnno(interval = 3000)
    public void exec0(SelectionKey[] keylist) {
        AbstractGunBaseLogUtil.info(keylist.length + " has been alived");
    }

    @Override
    public int runningTimes() {
        return -1;
    }
}
