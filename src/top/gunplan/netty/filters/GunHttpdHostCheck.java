package top.gunplan.netty.filters;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyPropertyManager;
import top.gunplan.netty.impl.GunRequestFilterDto;
import top.gunplan.netty.impl.example.GunResponseFilterDto;
import top.gunplan.netty.protocol.GunHttp2InputProtocl;

@GunNetFilterOrder(index = 1)
public class GunHttpdHostCheck implements GunNettyFilter {
    @Override
    public boolean doInputFilter(GunRequestFilterDto filterDto) {
        GunHttp2InputProtocl httpmessage = (GunHttp2InputProtocl) filterDto.getObject();
        return httpmessage.getRequstHead().get("Host").equals(GunNettyPropertyManager.getHttp().getHttphost());
    }

    @Override
    public boolean doOutputFilter(GunResponseFilterDto filterDto) {
        return true;
    }
}
