package top.gunplan.netty;

/**
 *
 */
public interface GunNettyFilter extends GunHandel {
    /**
     * doing filter when the request occur
     * @param filterDto input to the filter's deal Object
     */
    void doRequestFilter(GunFilterDto filterDto);

    /**
     *
     * @param filterDto input to the filter's deal Object
     */
    void doResponseFilter(GunFilterDto filterDto);
}
