package top.gunplan.netty.filters;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.GunInputFilterChecker;
import top.gunplan.netty.impl.example.GunOutputFilterDto;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunStdString;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder
public class GunStdToStringFilter implements GunNettyFilter {
    @Override
    public DealResult doInputFilter(GunInputFilterChecker filterDto) {
        GunStdString s = new GunStdString();
        s.unSerialize(filterDto.getSrc());
        filterDto.setObject(s);
        return DealResult.NEXT;
    }

    @Override
    public DealResult doOutputFilter(GunOutputFilterDto filterDto) {
        return DealResult.NEXT;
    }
}
