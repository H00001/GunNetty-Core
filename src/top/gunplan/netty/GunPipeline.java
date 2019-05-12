package top.gunplan.netty;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author dosdrtt
 * @since 1.0.0.1
 */
public interface GunPipeline {
    /**
     * register the handle in the chain
     *
     * @param handle register handle
     * @return chain style is self
     */
    GunPipeline register(GunHandle handle);

    /**
     * addTimer
     *
     * @param timer {@link GunTimer}
     * @return this, chain style
     */
    GunPipeline addTimer(GunTimer timer);

    /**
     * addFilter
     *
     * @param filter {@link GunNettyFilter}
     * @return this, chain style
     */
    GunPipeline addFilter(GunNettyFilter filter);

    /**
     * setHandle
     *
     * @param handle GunNettyHandle
     * @return this.chain style
     */
    GunPipeline setHandle(GunNettyHandle handle);

    /**
     * @param clazz refSetHandle
     * @return this,chain style
     * @throws IllegalAccessException exc
     * @throws InstantiationException exc
     */
    GunPipeline refSetHandle(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    /**
     * check the pileline model avilable
     *
     * @return check result
     */
    GunPilelineCheckResult check();


    List<GunNettyFilter> getFilters();


    GunNettyHandle getHandel();


    List<GunTimer> getTimer();

}
