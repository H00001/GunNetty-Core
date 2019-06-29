package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;

public class GunReadPropertyException extends GunException {
    public GunReadPropertyException(String why) {
        super(GunExceptionType.READ_PROPERTY_ERROR, why);
    }
}
