package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyOutputFilterChecker;

/**
 * GunNettyInboundFilter
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-19 14:18
 */
public interface GunNettyInboundFilter extends GunNettyFilter {


    /**
     * doOutputFilter
     *
     * @param filterDto input to the filter's deal Object
     * @return Deal result {@link top.gunplan.netty.GunNettyFilter.DealResult}
     * @throws GunChannelException channel error
     */
    @Override
    default DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws GunChannelException {
        return DealResult.NEXT;
    }
}
