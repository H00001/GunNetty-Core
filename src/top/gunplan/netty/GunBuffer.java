package top.gunplan.netty;

import sun.misc.Unsafe;


/**
 * @author dosdrtt
 */
public interface GunBuffer {
    class UnsafeInstanse {
        private static volatile Unsafe instance = null;

        private static Unsafe newUnsafe() throws Exception {
            if (instance == null) {
                synchronized (UnsafeInstanse.class) {
                    if (instance == null) {
                        instance = (Unsafe) Class.forName("sun.misc.Unsafe").getConstructor().newInstance();
                    }
                }
            }
            return instance;
        }
    }


    default Unsafe newUnsafe() throws Exception {
        return UnsafeInstanse.newUnsafe();
    }

    byte[] malloc(int size, int len) throws Exception;

    byte[] malloc(int len) throws Exception;

}
