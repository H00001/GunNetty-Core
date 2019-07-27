package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyInputFilterChecker;

/**
 * GunNettyOutboundFilter
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-27 08:09
 */

public interface GunNettyOutboundFilter extends GunNettyFilter {
    @Override
    default DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws GunChannelException {
        return DealResult.NEXT;
    }
}
