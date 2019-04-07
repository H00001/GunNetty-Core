package top.gunplan.netty.impl.propertys;

import sun.jvm.hotspot.debugger.cdbg.EnumType;

public class GunCoreProperty implements GunProPerty {
    public enum connectionType {
        /**
         *
         */
        KEEP_ALIVE(1, "keep-alive"), CLOSE(2, "close"), NODEF(4, "nodef");
        private int index;
        private String sval;

        public String getSVal() {
            return sval;
        }

        connectionType(int index, String sval) {
            this.index = index;
            this.sval = sval;
        }

        public static connectionType getTypeByval(int val) {
            for (connectionType type : connectionType.values()) {
                if (type.index == val) {
                    return type;
                }
            }
            return NODEF;
        }
    }

    private volatile int port;
    private volatile int maxRunnningNum;
    private volatile int clientWaitTime;
    private volatile int fileReadBufferMin;
    private volatile int connection;
    private volatile String profileName;

    public connectionType getConnection() {
        return connectionType.getTypeByval(connection);
    }

    public int getFileReadBufferMin() {
        return fileReadBufferMin;
    }

    public int getClientWaitTime() {
        return clientWaitTime;
    }

    public String getProfileName() {
        return profileName;
    }

    public int getPort() {
        return port;
    }

    public int getMaxRunnningNum() {
        return maxRunnningNum;
    }

}
