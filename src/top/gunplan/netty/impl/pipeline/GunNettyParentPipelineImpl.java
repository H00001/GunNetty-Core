/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunNettyHandleChangeObserve;
import top.gunplan.netty.GunPipelineCheckResult;

public class GunNettyParentPipelineImpl extends AbstractNettyPipelineImpl implements GunNettyParentPipeline {


    @Override
    public GunPipelineCheckResult check() {
        return null;
    }

    @Override
    public void setPipelineHandleChangeObserve(GunNettyHandleChangeObserve observe) {

    }
}
