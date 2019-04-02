package top.gunplan.netty.impl.propertys;

public class GunCoreProperty implements GunProPerty {
    private volatile int port;
    private volatile int maxRunnningNum;
    private volatile int clientWaitTime;
    private volatile int fileReadBufferMin;
    private volatile String profileName;

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
