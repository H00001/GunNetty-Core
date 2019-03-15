package top.gunplan.netty;

public interface GunNettyFilter {
    /**
     * doing filter when the request occur
     * @param filterDto input to the filter's deal Object
     */
    void doRequestFilter(GunFilterDto filterDto);
    void doResponseFilter(GunFilterDto filterDto);
}
