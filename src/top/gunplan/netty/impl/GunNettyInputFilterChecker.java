package top.gunplan.netty.impl;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.protocol.GunNetInbound;

import java.nio.channels.SelectionKey;


/**
 * GunNettyInputFilterChecker
 *
 * @author dosdrtt
 */
public final class GunNettyInputFilterChecker extends AbstractGunChecker<GunNetInbound> {


    public GunNettyInputFilterChecker(final SelectionKey key) {
        super(key);
    }

    @Override
    public void translate() {
        this.to.unSerialize(src);
    }

    public GunNettyInputFilterChecker(byte[] src, GunNetInbound object) {
        super(null);
        this.src = src;
        this.to = object;
    }


    @Override
    public <R extends GunNetInbound> boolean tranToObject(Class<R> in) {
        try {
            R instance = in.getDeclaredConstructor().newInstance();
            this.to = instance;
            return instance.unSerialize(src);
        } catch (ReflectiveOperationException e) {
            throw new GunException(GunExceptionType.TRANSLATE_ERROR, GunNettyInputFilterChecker.class.getName());
        }
    }

}
