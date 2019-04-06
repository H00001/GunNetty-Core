package top.gunplan.netty.impl;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.protocol.GunNetInputInterface;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author dosdrtt
 */
public final class GunRequestFilterDto {
    private SelectionKey key;
    private AtomicInteger size;



    public GunRequestFilterDto(SelectionKey key) {
        this.key = key;
        this.size = size;
    }

    public SelectionKey getKey() {
        return key;
    }

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


    public GunNetInputInterface getObject() {
        return object;
    }

    public GunRequestFilterDto(@NotNull byte[] src, GunNetInputInterface object) {
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
