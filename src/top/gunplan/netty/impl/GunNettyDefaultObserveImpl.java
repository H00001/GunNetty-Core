package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.impl.propertys.GunProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

/**
 * GunNettyDefaultObserveImpl
 *
 * @author dosdrtt
 */
public class GunNettyDefaultObserveImpl implements GunNettyObserve {
    @Override
    public void onBooted(GunProperty proPerty) {
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        GunNettyCoreProperty property = (GunNettyCoreProperty) proPerty;
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
