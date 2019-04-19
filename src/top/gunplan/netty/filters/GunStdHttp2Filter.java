package top.gunplan.netty.filters;
import top.gunplan.netty.impl.example.GunInputFilterChecker;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.example.GunOutputFilterChecker;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.protocol.GunHttp2InputProtocl;
import top.gunplan.netty.protocol.GunNetInputInterface;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder(index = 1)
public class GunStdHttp2Filter implements GunNettyFilter {
    @Override
    public DealResult doInputFilter(GunInputFilterChecker filterDto) {
        GunNetInputInterface protocl = new GunHttp2InputProtocl();
        if (protocl.unSerialize(filterDto.getSrc())) {
            filterDto.setObject(protocl);
            return DealResult.NEXT;
        } else {
            return DealResult.NOTDEALALLNEXT;
        }
    }

    @Override
    public DealResult doOutputFilter(GunOutputFilterChecker filterDto) {
        return DealResult.NEXT;
    }

}
