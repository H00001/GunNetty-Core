package top.gunplan.netty.filters;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.GunRequestFilterDto;
import top.gunplan.netty.impl.example.GunResponseFilterDto;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunStdString;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder
public class GunStdToStringFilter implements GunNettyFilter {
    @Override
    public boolean doRequestFilter(GunRequestFilterDto filterDto) {
        GunStdString s = new GunStdString() ;
        s.unSerialize(filterDto.getSrc());
        filterDto.setObject(s);
        return true;
    }

    @Override
    public boolean doResponseFilter(GunResponseFilterDto filterDto) {
        return false;
    }
}
