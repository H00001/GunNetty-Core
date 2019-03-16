package top.gunplan.netty.filters;

import top.gunplan.netty.GunFilterDto;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.filters.protocls.GunHttp2Protocl;

@GunNetFilterOrder
public class GunHttpFilter implements GunNettyFilter {
    @Override
    public void doRequestFilter(GunFilterDto filterDto) {
        GunRequestProtocl protocl =  new GunHttp2Protocl();
        protocl.unSeriz(filterDto.getSrc());
        filterDto.setObject(protocl);
    }

    @Override
    public void doResponseFilter(GunFilterDto filterDto) {

    }
}
