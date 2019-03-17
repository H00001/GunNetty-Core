package top.gunplan.netty.filters;

import top.gunplan.netty.GunFilterDto;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.protocol.GunRequestProtoclInterface;

@GunNetFilterOrder
public class GunStdHttp2Filter implements GunNettyFilter {
    @Override
    public void doRequestFilter(GunFilterDto filterDto) {
        GunRequestProtoclInterface protocl =  new GunHttp2RequestProtocl();
        protocl.unSeriz(filterDto.getSrc());
        filterDto.setObject(protocl);
    }

    @Override
    public void doResponseFilter(GunFilterDto filterDto) {

    }
}
