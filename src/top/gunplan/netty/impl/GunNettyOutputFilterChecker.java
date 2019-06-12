package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetOutputInterface;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see GunNettyChecker
 */
public final class GunNettyOutputFilterChecker extends AbstractGunChecker {

    private GunNetOutputInterface outputObject;


    /**
     * GunNettyOutputFilterChecker
     *
     * @param outputObject GunNetOutputInterface
     * @param key     SelectionKey
     */
    GunNettyOutputFilterChecker(GunNetOutputInterface outputObject, SelectionKey key) {
        super(key);
        this.outputObject = outputObject;
    }


    GunNettyOutputFilterChecker(GunNetOutputInterface outputObject) {
        super(null);
        this.outputObject = outputObject;
    }

    public GunNetOutputInterface getOutputObject() {
        return outputObject;
    }

    public void setOutputObject(GunNetOutputInterface outputObject) {
        this.outputObject = outputObject;
    }

    GunNettyOutputFilterChecker() {
        super(null);
    }
}
