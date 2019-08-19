/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * SystemChannelChangedHandle
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 09:28
 */
public interface SystemChannelChangedHandle extends GunHandle {

    default void whenBind(int port) {
        System.out.println("lestion on " + port);
    }
}
