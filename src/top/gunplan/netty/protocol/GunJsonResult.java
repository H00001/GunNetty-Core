package top.gunplan.netty.protocol;

import top.gunplan.netty.protocol.resputil.GunMappingJsonResp;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public class GunJsonResult extends GunMappingJsonResp {
    public static <T extends Collection> GunMappingJsonResp GunJsonResult(T object) {
        if (object instanceof Collection) {

        }
        return null;
    }

    public static <T extends Map> GunMappingJsonResp GunJsonResult(T object) {
        if (object instanceof Collection) {

        }
        return null;
    }

    public static <T extends CharSequence> GunMappingJsonResp GunJsonResult(T object) {
        if (object instanceof Collection) {

        }
        return null;
    }

}
