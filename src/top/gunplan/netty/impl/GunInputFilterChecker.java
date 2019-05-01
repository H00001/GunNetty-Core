package top.gunplan.netty.impl;
import top.gunplan.netty.protocol.GunNetInputInterface;
import java.nio.channels.SelectionKey;


/**
 * @author dosdrtt
 */
public final class GunInputFilterChecker extends AbstractGunChecker {


    private byte[] src;
    private boolean writeable = true;

    public void setSrc(byte[] src) {
        this.src = src;
    }

    public boolean isWriteable() {
        return writeable;
    }

    public void setWriteable(boolean writeable) {

        this.writeable = writeable;
    }

    private GunNetInputInterface object;

    public GunInputFilterChecker(final SelectionKey key) {
        super(key);
    }

    public GunNetInputInterface getObject() {
        return object;
    }

    public GunInputFilterChecker( byte[] src, GunNetInputInterface object) {
        super(null);
        this.src = src;
        this.object = object;
    }

    public byte[] getSrc() {
        return src;
    }

    public GunNetInputInterface getGunRequestProtoclObject() {
        return object;
    }

    public void setObject(GunNetInputInterface object) {
        this.object = object;
    }
}
