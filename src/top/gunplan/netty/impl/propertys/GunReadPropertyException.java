package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionTypes;

public class GunReadPropertyException extends GunException {
    public GunReadPropertyException(String why) {
        super(GunExceptionTypes.READPROPERTYERROR, why);
    }
}
