package top.gunplan.netty;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author dosdrtt
 * @since 1.0.0.1
 */
public interface GunNettyPipeline {
    /**
     * register the handle in the chain
     *
     * @param handle register handle
     * @return chain style is self
     */
    GunNettyPipeline register(GunHandle handle);

    /**
     * addTimer
     *
     * @param timer {@link GunNettyTimer}
     * @return this, chain style
     */
    GunNettyPipeline addTimer(GunNettyTimer timer);

    /**
     * addFilter
     *
     * @param filter {@link GunNettyFilter}
     * @return this, chain style
     */
    GunNettyPipeline addFilter(GunNettyFilter filter);

    /**
     * setHandle
     *
     * @param handle GunNettyHandle
     * @return this.chain style
     */
    GunNettyPipeline setHandle(GunNettyHandle handle);

    /**
     * ref to set the handle
     *
     * @param clazz refSetHandle
     * @return GunNettyPipeline this,chain style
     * @throws IllegalAccessException exc
     * @throws InstantiationException exc
     * @throws InvocationTargetException exc
     * @throws NoSuchMethodException can not find this method
     */
    GunNettyPipeline refSetHandle(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    /**
     * check the pileline model avilable
     *
     * @return check result
     */
    GunPipelineCheckResult check();


    /**
     * getFilters
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    List<GunNettyFilter> getFilters();


    /**
     * getHandel
     *
     * @return GunNettyHandle
     */
    GunNettyHandle getHandel();


    /**
     * getTimer
     *
     * @return List<GunNettyTimer>
     */
    List<GunNettyTimer> getTimer();


    /**
     * init
     * <p>
     * be called at init
     *
     * @return int
     */
    int init();

}
