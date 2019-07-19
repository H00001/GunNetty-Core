package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyInputFilterChecker;

public interface GunNettyOutboundFilter extends GunNettyFilter {
    @Override
    default DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws GunChannelException {
        return DealResult.NEXT;
    }
}
