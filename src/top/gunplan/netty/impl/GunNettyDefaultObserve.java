/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.netty.observe.GunNettyBaseObserve;
import top.gunplan.netty.observe.GunNettyServicesObserve;
import top.gunplan.utils.GunLogger;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * GunNettyDefaultObserve
 *
 * @author dosdrtt
 */
public class GunNettyDefaultObserve implements GunNettyServicesObserve, GunNettyBaseObserve {

    @Override
    public void onBooted(GunNettyCoreProperty property) {
        final GunLogger logger = GunNettyContext.logger.setTAG(GunNettyDefaultObserve.class);
        logger.info("A high performance net server and a reverse proxy server", "BOOTED");
   //     try {
      //      logger.outputFile(property.getProfileName());
    //    } catch (IOException | URISyntaxException e) {
    //        logger.error(e);
    //    }
    }

    @Override
    public boolean onBooting(GunNettyCoreProperty property) {
        return true;
    }

    @Override
    public void onStop(GunNettyCoreProperty property) {
        GunNettyContext.logger.info("SERVER STOP");
    }

    @Override
    public void onStatusChanged(GunNettyChangeStatus status) {

    }


}
