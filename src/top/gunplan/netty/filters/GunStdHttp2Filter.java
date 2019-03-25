package top.gunplan.netty.filters;

import top.gunplan.netty.GunRequestFilterDto;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunResponseFilterDto;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder
public class GunStdHttp2Filter implements GunNettyFilter {
    @Override
    public boolean doRequestFilter(GunRequestFilterDto filterDto) {
        GunNetRequestInterface protocl = new GunHttp2RequestProtocl();
        protocl.unSerialize(filterDto.getSrc());
        filterDto.setObject(protocl);
        return true;
    }

    @Override
    public boolean doResponseFilter(GunResponseFilterDto filterDto) {
        return true;
    }

}
