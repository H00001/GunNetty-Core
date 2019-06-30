package top.gunplan.netty;


import top.gunplan.netty.impl.GunNettyInputFilterChecker;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;


/**
 * filter is a type of handle
 *
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {
    /**
     * doInputFilter
     *
     * @param filterDto input filter dto
     * @return deal result {@link DealResult};
     * @throws Exception kinds of exception
     */
    DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws Exception;

    /**
     * doing filter when the response occur
     *
     * @param filterDto input to the filter's deal Object
     * @return DealResult result true:next false:break
     * @throws Exception kinds of exception
     */
    DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws Exception;

    /**
     * doOutputFilter
     *
     * @param filterDto filter dto
     * @param channel   channel to transfer
     * @return deal result
     * @throws IOException i/o exception
     */
    default DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto, SocketChannel channel) throws IOException {
        return DealResult.NEXT;
    }


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


    public enum DealResult {
        /**
         * NATALINA  :do not deal input filter ,go for handle right away
         * CLOSE         :do not deal any filter or handle
         * NEXT          :nothing will happened
         * NOTDEALOUTPUT :exit output filter chain and handle but it wasn't close
         * NOTDEALALLNEXT:ecit all filter chain and handle but it wasn't close
         */
        NATALINA, CLOSE, NEXT, NOTDEALOUTPUT, NOTDEALALLNEXT;
    }
}
