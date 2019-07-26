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


    @Override
    default DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws GunChannelException {
        return DealResult.NEXT;
    }
}
