package top.gunplan.netty;


/**
 * @author dosdrtt
 * @date 2019/05/25
 */
public interface GunBootServerBase {
    /**
     * start sync server and wait
     * @return int boot resuk=lt
     * @throws Exception  syncing's exception
     */

    int sync() throws Exception;
}
