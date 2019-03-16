package top.gunplan.netty.anno;

import top.gunplan.netty.GunHandle;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GunAutoWired {
    Class<? extends GunHandle> classname();
}

