package top.gunplan.netty.anno;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoWired {
    Class<?> classname();


}

