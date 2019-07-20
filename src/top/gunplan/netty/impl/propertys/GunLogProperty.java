package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyContext;
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


    public GunLogProperty() {
    }


    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean doRegex() {
        GunLogger log = GunNettyContext.logger;
        String f = "file:";
        if (isAvailable()) {
            log.setLevel(outputlevel);
            log.info("Check parameters succeed");
            if (format != null) {
                log.setFormat(format);
            }
            final String direct = this.direct;
            if (direct.startsWith(f)) {
                String[] prfile = direct.replace("file:", "").split(",");
                try {
                    log.setStdoutput(new FileOutputStream(prfile[0], true));
                    log.setErrOutput(new FileOutputStream(prfile[1], true));

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
