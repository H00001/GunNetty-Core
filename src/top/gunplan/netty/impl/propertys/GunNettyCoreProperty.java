package top.gunplan.netty.impl.propertys;


import top.gunplan.netty.GunProperty;
import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.utils.GunBytesUtil;

/**
 * core property
 *
 * @author dosdrtt
 */
@GunPropertyMap(name = "core")
public class GunNettyCoreProperty implements GunProperty {
    private GunNettyCoreProperty() {

    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean doRegex() {
        if (isAvailable()) {
            GunBytesUtil.init(fileReadBufferMin);
            return true;
        } else {
            return false;
        }
    }

    private volatile int initWait;
    private volatile int minInterval;

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

    public long initWait() {
        return initWait;
    }

    public long minInterval() {
        return minInterval;
    }

    private volatile int maxRunningNum;
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

    public int maxRunningNum() {
        return maxRunningNum;
    }

}
