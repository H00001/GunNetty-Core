/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyStringUtil;
import top.gunplan.utils.GunLogger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author dosdrtt
 * @see GunProperty
 */
@GunPropertyMap(name = "log")
public class GunLogProperty implements GunProperty {
    private int outputlevel;
    private String direct;
    private String format = null;
    private final String FILE = "file:";


    public GunLogProperty() {
    }


    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean doRegex() {
        GunLogger log = GunNettyContext.logger;
        if (isAvailable()) {
            log.setLevel(outputlevel);
            log.info("Check parameters succeed");
            if (format != null) {
                log.setFormat(format);
            }
            final String direct = this.direct;
            if (direct.startsWith(FILE)) {
                String[] profile = direct.replace(FILE, GunNettyStringUtil.EMPTY).split(",");
                try {
                    log.setStdOutput(new FileOutputStream(profile[0], true));
                    log.setErrOutput(new FileOutputStream(profile[1], true));
                } catch (FileNotFoundException e) {
                    log.error(e);
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
