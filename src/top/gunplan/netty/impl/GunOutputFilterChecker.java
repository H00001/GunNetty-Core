package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetOutputInterface;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see top.gunplan.netty.impl.GunChecker
 */
public final class GunOutputFilterChecker extends AbstractGunChecker {

    private GunNetOutputInterface outputObject;


    /**
     * GunOutputFilterChecker
     *
     * @param outputObject GunNetOutputInterface
     * @param key     SelectionKey
     */
    public GunOutputFilterChecker(GunNetOutputInterface outputObject, SelectionKey key) {
        super(key);
        this.outputObject = outputObject;
    }


    GunOutputFilterChecker(GunNetOutputInterface outputObject) {
        super(null);
        this.outputObject = outputObject;
    }

    public GunNetOutputInterface getOutputObject() {
        return outputObject;
    }

    public void setOutputObject(GunNetOutputInterface outputObject) {
        this.outputObject = outputObject;
    }

    GunOutputFilterChecker() {
        super(null);
    }
}
