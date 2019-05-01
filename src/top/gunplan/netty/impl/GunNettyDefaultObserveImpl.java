package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunProPerty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

class GunNettyDefaultObserveImpl implements GunNettyObserve {
    @Override
    public void onBoot(GunProPerty proPerty) {
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        GunCoreProperty property = (GunCoreProperty) proPerty;
        AbstractGunBaseLogUtil.outputFile(property.getProfileName());
    }

    @Override
    public void onStop(GunProPerty proPerty) {


    }

    @Override
    public void onStatusChanged(GunNettyStatus status) {

    }
}
