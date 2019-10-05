/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;


import top.gunplan.netty.GunProperty;
import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.utils.GunBytesUtil;

/**
 * core property
 *
 * @author dosdrtt
 */
@GunPropertyMap(name = "core")
public class GunNettyCoreProperty implements GunProperty {
    private volatile int initWait;
    private volatile int minInterval;
    private volatile int port;
    private volatile int maxRunningNum;
    private volatile int clientWaitTime;
    private volatile int fileReadBufferMin;
    private volatile String profileName;

    private GunNettyCoreProperty() {

    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean doRegex() {
        if (isAvailable()) {
            GunBytesUtil.init(fileReadBufferMin);
            return true;
        } else {
            return false;
        }
    }


    public long initWait() {
        return initWait;
    }

    public long minInterval() {
        return minInterval;
    }


    public int getFileReadBufferMin() {
        return fileReadBufferMin;
    }

    public int getClientWaitTime() {
        return clientWaitTime;
    }

    public String getProfileName() {
        return profileName;
    }

    public int getPort() {
        return port;
    }

    public int maxRunningNum() {
        return maxRunningNum;
    }

}
