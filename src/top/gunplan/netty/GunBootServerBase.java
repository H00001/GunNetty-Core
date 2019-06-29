package top.gunplan.netty;


/**
 * @author dosdrtt
 * @date 2019/05/25
 */
@FunctionalInterface
public interface GunBootServerBase {
    /**
     * sync
     * start sync server and wait
     * @return int boot result
     * @throws Exception  syncing's exception
     */

    int sync() throws Exception;
}
