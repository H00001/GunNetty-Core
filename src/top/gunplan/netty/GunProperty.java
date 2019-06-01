package top.gunplan.netty;

/**
 * this is a property interface
 * all of class that implements
 * can as a property class
 * property class do not have
 * get method ,all of filed will
 * be set by reflect.
 *
 * @author dosdrtt
 */
public interface GunProperty {
    /**
     * check is available
     *
     * @return is this property object available
     */
    boolean isAvailable();

    /**
     * do regex
     *
     * @return regex the object
     */

    default boolean doRegex() {
        return isAvailable();
    }
}
