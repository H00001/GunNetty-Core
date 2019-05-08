package top.gunplan.netty.impl.propertys;

/**
 * @author dosdrtt
 * @see GunProperty
 *
 */
public class GunLogProperty implements GunProperty {
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
}
