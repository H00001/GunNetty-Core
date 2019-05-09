package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

class GunNettyDefaultObserveImpl implements GunNettyObserve {
    @Override
    public void onBooted(GunProperty proPerty) {
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        GunCoreProperty property = (GunCoreProperty) proPerty;
        try {
            AbstractGunBaseLogUtil.outputFile(property.getProfileName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onBooting(GunProperty proPerty) {
        return true;
    }

    @Override
    public void onStop(GunProperty proPerty) {

    }

    @Override
    public void onStatusChanged(GunNettyStatus status) {

    }
}
