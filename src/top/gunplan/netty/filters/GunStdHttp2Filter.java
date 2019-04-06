package top.gunplan.netty.filters;

import top.gunplan.netty.impl.GunRequestFilterDto;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.example.GunOutputFilterDto;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunHttp2InputProtocl;
import top.gunplan.netty.protocol.GunNetInputInterface;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder(index = 1)
public class GunStdHttp2Filter implements GunNettyFilter {
    @Override
    public DealResult doInputFilter(GunRequestFilterDto filterDto) {
        GunNetInputInterface protocl = new GunHttp2InputProtocl();
        protocl.unSerialize(filterDto.getSrc());
        filterDto.setObject(protocl);
        return DealResult.NEXT;
    }

    @Override
    public DealResult doOutputFilter(GunOutputFilterDto filterDto) {
        return DealResult.NEXT;
    }

}
