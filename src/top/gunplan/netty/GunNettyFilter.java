package top.gunplan.netty;


import top.gunplan.netty.impl.GunRequestFilterDto;
import top.gunplan.netty.impl.example.GunOutputFilterDto;

/**
 * filter is a type of handle
 *
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {
    /**
     * doing filter when the request occur
     *
     * @param filterDto input to the filter's deal Object
     * @return filter result true:next false:break
     */

    public enum DealResult {
        /**
         *
         */
        NOTDEALINPUT, CLOSE, NEXT, NOTDEALOUTPUT, NOTDEALALLNEXT;
    }

    DealResult doInputFilter(GunRequestFilterDto filterDto) throws Exception;

    /**
     * doing filter when the response occur
     *
     * @param filterDto input to the filter's deal Object
     * @return filter result true:next false:break
     */
    DealResult doOutputFilter(GunOutputFilterDto filterDto) throws Exception;
}
