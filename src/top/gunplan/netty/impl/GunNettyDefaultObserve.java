/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyObserve;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.utils.GunLogger;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * GunNettyDefaultObserve
 *
 * @author dosdrtt
 */
public class GunNettyDefaultObserve implements GunNettyObserve {

    @Override
    public void onBooted(GunNettyCoreProperty property) {
        final GunLogger logger = GunNettyContext.logger.setTAG(GunNettyDefaultObserve.class);
        logger.info("A high performance net server and a reverse proxy server", "BOOTED");
        try {
            logger.outputFile(property.getProfileName());
        } catch (IOException | URISyntaxException e) {
            logger.error(e);
        }
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
