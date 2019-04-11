package top.gunplan.netty.anno;

import top.gunplan.netty.GunHandle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GunAutoWired {
    Class<? extends GunHandle> classname();
}

