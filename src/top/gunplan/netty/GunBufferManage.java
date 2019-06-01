package top.gunplan.netty;




/**
 * @author dosdrtt
 */
public interface GunBufferManage {
//    class UnsafeInstanse {
//        private static volatile Unsafe instance = null;
//
//        private static Unsafe newUnsafe() throws Exception {
//            if (instance == null) {
//                synchronized (UnsafeInstanse.class) {
//                    if (instance == null) {
//                        instance = (Unsafe) Class.forName("sun.misc.Unsafe").getConstructor().newInstance();
//                    }
//                }
//            }
//            return instance;
//        }
//    }

    /**
     * addEvent
     */
    void addEvent();

    /**
     * descEvent
     */
    void descEvent();

//    default Unsafe newUnsafe() throws Exception {
//        return UnsafeInstanse.newUnsafe();
//    }

    /**
     * malloc like c
     * @param len length
     * @return {@link GunLinkedData} data
     * @throws Exception kinds of exception
     */
    GunLinkedData malloc(int len) throws Exception;

}
