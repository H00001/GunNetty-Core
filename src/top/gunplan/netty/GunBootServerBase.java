package top.gunplan.netty;

public interface GunBootServerBase {
    /**
     * start sync server and wait
     * @return int boot resuk=lt
     * @throws Exception  syncing's exception
     */

    int sync() throws Exception;
}
