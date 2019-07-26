package top.gunplan.netty.example;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyInboundFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.impl.GunNettyInputFilterChecker;

/**
 * GunNettyCharsetInboundChecker
 * an example
 *
 * @author frank albert
 * @version 0.0.0.3
 * @date 2019-07-26 23:21
 */
@GunNetFilterOrder(index = 1)
public class GunNettyCharsetInboundChecker implements GunNettyInboundFilter {

    @Override
    public DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws GunChannelException {
        if (filterDto.tranToObject(GunString.class)) {
            return DealResult.NEXT;
        } else {
            return DealResult.NOTDEALOUTPUT;
        }
    }

}
