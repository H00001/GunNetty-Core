package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetResponseInterface;

/**
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {
    /**
     * doing filter when the request occur
     *
     * @param filterDto input to the filter's deal Object
     */
    boolean doRequestFilter(GunRequestFilterDto filterDto);

    /**
     * @param filterDto input to the filter's deal Object
     */
    boolean doResponseFilter(GunResponseFilterDto filterDto);
}
