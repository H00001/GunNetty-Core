/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.GunNettyPipeline;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * BaseGunNettyWorker real worker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunNettyPipeline pipeline;
    final GunNettyHandle handle;
    private final AtomicInteger waitSize;


    BaseGunNettyWorker(final GunNettyPipeline pipeline, final AtomicInteger waitSize) {
        this.pipeline = pipeline;
        this.handle = pipeline.handel();
        this.waitSize = waitSize;

    }


    @Override
    public int decreaseChannel(int sum) {
        if (waitSize != null) {
            for (; ; ) {
                final int nowValue = waitSize.get();
                final int except = nowValue - sum;
                if (this.waitSize.compareAndExchangeRelease(nowValue, except) == except) {
                    return except;
                }
            }
        }
        return -32768;
    }

}
