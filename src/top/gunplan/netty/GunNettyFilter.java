package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetResponseInterface;

/**
 *
 */
public interface GunNettyFilter extends GunHandle {
    /**
     * doing filter when the request occur
     *
     * @param filterDto input to the filter's deal Object
     */
    void doRequestFilter(GunRequestFilterDto filterDto);

    /**
     * @param filterDto input to the filter's deal Object
     */
    void doResponseFilter(GunResponseFilterDto filterDto);
}
