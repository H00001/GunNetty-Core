package top.gunplan.netty.filters;

/**
 *
 * @author dosdrtt
 */
public interface GunRequestProtocl {
    byte[] seriz();
    boolean unSeriz(byte[] in);

}
