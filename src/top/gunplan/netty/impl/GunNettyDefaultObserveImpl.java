package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.GunLogger;

/**
 * GunNettyDefaultObserveImpl
 *
 * @author dosdrtt
 */
public class GunNettyDefaultObserveImpl implements GunNettyObserve {
    private static final GunLogger LOG = GunNettyContext.logger.setTAG(GunNettyDefaultObserveImpl.class);

    @Override
    public void onBooted(GunNettyCoreProperty property) {
        LOG.debug("A high performance net server and a reverse proxy server", "BOOTED");
        try {
            LOG.outputFile(property.getProfileName());
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean onBooting(GunNettyCoreProperty property) {
        return true;
    }

    @Override
    public void onStop(GunNettyCoreProperty property) {
        LOG.info("SERVER STOP");
    }

    @Override
    public void onStatusChanged(GunNettyChangeStatus status) {

    }
}
