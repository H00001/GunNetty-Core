package top.gunplan.netty;

import java.io.PrintStream;

/**
 * @author dosdrtt
 */
public abstract class BaseGunLog {
    private static final int BASELEVEL = 1;
    private static volatile int level = 4;
    private static volatile PrintStream stdoutput = System.out;
    private static volatile PrintStream erroutput = System.err;


    public static void info(String s) {
        if (BaseGunLog.level <= (BASELEVEL)) {
            BaseGunLog.stdoutput.println("[LOG][INFOR] "+s.replace("\n","\n\t")+"");
        }
    }

    public static void debug(String s) {
        if (BaseGunLog.level <= (BASELEVEL<<1)) {
            BaseGunLog.stdoutput.println("[LOG][DEBUG] "+s+"");
        }
    }

    public static void error(String s) {
        if (BaseGunLog.level <= (BASELEVEL<<2)) {
            BaseGunLog.erroutput.println("[LOG][ERROR] "+s+"");
        }
    }

    public static void urgency(String s) {
        if (BaseGunLog.level <= (BASELEVEL<<3)) {
            BaseGunLog.erroutput.println("[LOG][URGEN] "+"");
        }
    }

    public static void setLevel(int level) {
        BaseGunLog.level = level;
    }
}
