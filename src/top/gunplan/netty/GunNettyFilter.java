package top.gunplan.netty;


/**
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {
    /**
     * doing filter when the request occur
     *
     * @param filterDto input to the filter's deal Object
     * @return filter result true:next false:break
     *
     */
    boolean doRequestFilter(GunRequestFilterDto filterDto);

    /**
     * doing filter when the response occur
     * @param filterDto input to the filter's deal Object
     * @return filter result true:next false:break
     */
    boolean doResponseFilter(GunResponseFilterDto filterDto);
}
