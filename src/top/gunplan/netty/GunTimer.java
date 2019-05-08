package top.gunplan.netty;

/**
 * this is a time execute interface
 * you can use this with {@link }
 */
public interface GunTimer extends GunHandle {
    /**
     *
     * @return -1 is always
     */
    int runningTimes();
}
