package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
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
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server", "BOOTED");
        try {
            AbstractGunBaseLogUtil.outputFile(property.getProfileName());
        } catch (Exception e) {
            AbstractGunBaseLogUtil.error(e);
        }

    }

    @Override
    public boolean onBooting(GunNettyCoreProperty property) {
        return true;
    }

    @Override
    public void onStop(GunNettyCoreProperty property) {
        AbstractGunBaseLogUtil.info("SERVER STOP");
    }

    @Override
    public void onStatusChanged(GunNettyChangeStatus status) {

    }
}
