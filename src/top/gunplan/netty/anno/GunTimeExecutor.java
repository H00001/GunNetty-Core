/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GunTimeExecutor {
    /**
     * get time Interval
     *
     * @return time interval
     */
    int interval();


    int maxRunningTimes() default -1;


    boolean available() default true;


    GunHandleTag t();
}
