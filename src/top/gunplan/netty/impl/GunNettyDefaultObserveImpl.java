package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

/**
 * GunNettyDefaultObserveImpl
 *
 * @author dosdrtt
 */
public class GunNettyDefaultObserveImpl implements GunNettyObserve {
    @Override
    public void onBooted(GunNettyCoreProperty property) {
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        try {
            AbstractGunBaseLogUtil.outputFile(property.getProfileName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onBooting(GunNettyCoreProperty proPerty) {
        return true;
    }

    @Override
    public void onStop(GunNettyCoreProperty proPerty) {

    }

    @Override
    public void onStatusChanged(GunNettyStatus status) {

    }
}
