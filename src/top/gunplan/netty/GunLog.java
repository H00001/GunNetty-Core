package top.gunplan.netty;

import java.io.PrintStream;

/**
 * @author dosdrtt
 */
public abstract class GunLog {
    private static final int BASELEVEL = 1;
    private static volatile int level = 4;
    private static volatile PrintStream stdoutput = System.out;
    private static volatile PrintStream erroutput = System.err;


    public static void info(String s) {
        if (GunLog.level <= (BASELEVEL)) {
            GunLog.stdoutput.println("[LOG][INFOR] "+s.replace("\n","\n\t")+"");
        }
    }

    public static void debug(String s) {
        if (GunLog.level <= (BASELEVEL<<1)) {
            GunLog.stdoutput.println("[LOG][DEBUG] "+s+"");
        }
    }

    public static void error(String s) {
        if (GunLog.level <= (BASELEVEL<<2)) {
            GunLog.erroutput.println("[LOG][ERROR] "+s+"");
        }
    }

    public static void urgency(String s) {
        if (GunLog.level <= (BASELEVEL<<3)) {
            GunLog.erroutput.println("[LOG][URGEN] "+"");
        }
    }

    public static void setLevel(int level) {
        GunLog.level = level;
    }
}
