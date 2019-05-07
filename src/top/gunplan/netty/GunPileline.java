package top.gunplan.netty;


import java.util.List;
import java.util.Timer;

/**
 * @author dosdrtt
 * @since 1.0.0.1
 */
public interface GunPileline {
    /**
     * register the handle in the chain
     * @param handle register handle
     * @return chain style is self
     */
    GunPileline register(GunHandle handle);

    /**
     *
     * @param timer {@link GunTimer}
     * @return this,chain style
     */
    GunPileline addTimer(GunTimer timer);
    /**
     *
     * @param filter {@link GunNettyFilter}
     * @return this, chain style
     */
    GunPileline addFilter(GunNettyFilter filter);

    /**
     *
     * @param handle
     * @return
     */
    GunPileline setHandle(GunNettyHandle handle);

    /**
     *
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    GunPileline refSetHandle(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException;

    /**
     * check the pileline model avilable
     * @return check result
     */
    GunPilelineCheckResult check();


    List<GunNettyFilter> getFilters();



    GunNettyHandle getHandel();


    List<GunTimer> getTimer();

}
