package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.netty.GunProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

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
        String f = "file:";
        if (isAvailable()) {
            AbstractGunBaseLogUtil.setLevel(outputlevel);
            AbstractGunBaseLogUtil.debug("Check parameters succeed");
            if (format != null) {
                AbstractGunBaseLogUtil.setFormat(format);
            }
            final String direct = this.direct;
            if (direct.startsWith(f)) {
                String[] prfile = direct.replace("file:", "").split(",");
                try {
                    AbstractGunBaseLogUtil.setStdoutput(new FileOutputStream(prfile[0], true));
                    AbstractGunBaseLogUtil.setErroutput(new FileOutputStream(prfile[1], true));

                } catch (FileNotFoundException e) {

                    AbstractGunBaseLogUtil.error(e);
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
