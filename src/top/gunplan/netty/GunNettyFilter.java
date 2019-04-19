package top.gunplan.netty;


import top.gunplan.netty.impl.example.GunInputFilterChecker;
import top.gunplan.netty.impl.example.GunOutputFilterChecker;

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
     */
    DealResult doOutputFilter(GunOutputFilterChecker filterDto) throws Exception;
}
