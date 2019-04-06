package top.gunplan.netty.filters;

import top.gunplan.netty.impl.GunRequestFilterDto;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.example.GunResponseFilterDto;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunHttp2InputProtocl;
import top.gunplan.netty.protocol.GunNetInputInterface;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder(index = 0)
public class GunStdHttp2Filter implements GunNettyFilter {
    @Override
    public boolean doInputFilter(GunRequestFilterDto filterDto) {
        GunNetInputInterface protocl = new GunHttp2InputProtocl();
        protocl.unSerialize(filterDto.getSrc());
        filterDto.setObject(protocl);
        return true;
    }

    @Override
    public boolean doOutputFilter(GunResponseFilterDto filterDto) {
        return true;
    }

}
