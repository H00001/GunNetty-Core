package top.gunplan.netty;

import java.nio.channels.SelectionKey;
import java.util.Set;

/**
 * @author dosdrtt
 * @apiNote 0.1.0.3
 * this is a time execute interface
 * you can use this with {@link }
 */
public interface GunNettyTimer extends GunHandle {
    /**
     * get interval
     *
     * @return -1 is always
     */
    int interval();


    /**
     * get run times
     *
     * @return run times
     */
    int runingTimes();


    /**
     * avaliable keys
     *
     * @param keys selection keys set
     * @return work result
     */
    int doWork(Set<SelectionKey> keys);


    /**
     * ifKeyEmptyExec
     *
     * @return is or not exec
     */
    default boolean ifKeyEmptyExec() {
        return true;
    }

}
