/*
 * Copyright (c) frankHan personal 2017-2018 
 */

package top.gunplan.netty.impl;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * GunNettySelectorFactoryImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-12-21 09:20
 */
class GunNettySelectorFactoryImpl implements GunNettySelectorFactory {
    @Override
    public Selector allocate() {
        try {
            return Selector.open();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void release(Selector selector) {
        try {
            selector.selectNow();
            selector.close();
        } catch (IOException e) {
        }
    }
}
