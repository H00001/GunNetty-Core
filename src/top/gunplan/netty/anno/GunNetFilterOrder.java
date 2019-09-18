/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GunNetFilterOrder
 * the order of pipeline
 *
 * @author frank albert
 * #date 2019-09-18 07:46
 * @version 0.0.0.1
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GunNetFilterOrder {
    int index() default 0;
}
