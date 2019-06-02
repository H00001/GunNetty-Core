package top.gunplan.netty;


import top.gunplan.netty.impl.GunInputFilterChecker;
import top.gunplan.netty.impl.GunOutputFilterChecker;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;


/**
 * filter is a type of handle
 *
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {


    enum DealResult {
        /**
         * NOTDEALINPUT  :do not deal input filter ,go for handle right away
         * CLOSE         :do not deal any filter or handle
         * NEXT          :nothing will happened
         * NOTDEALOUTPUT :exit output filter chain and handle but it wasn't close
         * NOTDEALALLNEXT:ecit all filter chain and handle but it wasn't close
         */
        NOTDEALINPUT, CLOSE, NEXT, NOTDEALOUTPUT, NOTDEALALLNEXT;
    }

    /**
     * doInputFilter
     *
     * @param filterDto input filter dto
     * @return deal result {@link DealResult};
     * @throws Exception kinds of exception
     */
    DealResult doInputFilter(GunInputFilterChecker filterDto) throws Exception;

    /**
     * doing filter when the response occur
     *
     * @param filterDto input to the filter's deal Object
     * @return DealResult result true:next false:break
     * @throws Exception kinds of exception
     */
    DealResult doOutputFilter(GunOutputFilterChecker filterDto) throws Exception;


    /**
     * doConnFilter
     *
     * @param ch channel
     * @return is or not continue
     * @apiNote 0.1.2.3
     */
    default boolean doConnFilter(Channel ch) {
        return true;
    }


    /**
     * doOutputFilter
     *
     * @param filterDto filter dto
     * @param channel   channel to transfer
     * @return deal result
     * @throws Exception kind os exception
     */
    default DealResult doOutputFilter(GunOutputFilterChecker filterDto, SocketChannel channel) throws Exception {
        return DealResult.NEXT;
    }
}
