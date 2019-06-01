package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author dosdrtt
 * @see GunCoreProperty
 */
@GunPropertyMap(name = "log")
public class GunLogProperty implements GunCoreProperty {
    private int outputlevel;
    private String direct;

    public String getDirect() {
        return direct;
    }

    public GunLogProperty() {
    }

    public int getOutputlevel() {
        return outputlevel;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean doRegex() {
        if (isAvailable()) {
            AbstractGunBaseLogUtil.setLevel(outputlevel);
            AbstractGunBaseLogUtil.debug("Check parameters succeed");
            final String direct = this.direct;
            if (direct.startsWith("file:")) {
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
