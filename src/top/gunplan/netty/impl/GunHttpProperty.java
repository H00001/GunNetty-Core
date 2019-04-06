package top.gunplan.netty.impl;

import top.gunplan.netty.impl.propertys.GunProPerty;

/**
 * @author dosdrtt
 */
public class GunHttpProperty implements GunProPerty {
    private String scannPacket = null;

    private String  httphost = null;


    public String getScannPacket() {
        return scannPacket;
    }

    public String getHttphost() {
        return httphost;
    }

    public GunHttpProperty() {

    }
}
