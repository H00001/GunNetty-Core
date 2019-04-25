package top.gunplan.netty.protocol;

import top.gunplan.netty.protocol.resputil.GunMappingJsonResp;

import java.util.Collection;
import java.util.Map;

/**
 * @author dosdrtt
 */
public class GunJsonResult extends GunMappingJsonResp {
    private static final long serialVersionUID = 3828767872076068786L;

    @Deprecated
    public static <T extends Collection> GunMappingJsonResp gunJsonResult(T object) {

        return null;
    }

    public static <T extends Map> GunMappingJsonResp gunJsonResult(T object) {
        return null;
    }

    public static <T extends CharSequence> GunMappingJsonResp gunJsonResult(T object) {
        return null;
    }

}
