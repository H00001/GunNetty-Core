/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author frankHan
 * @date 2019-09-13
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GunHandleTag {
    long id();

    String name();
}
