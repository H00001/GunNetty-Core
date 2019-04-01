package top.gunplan.netty.impl.propertys;

public class GunCoreProperty {
    private volatile  int port;
    private volatile  int maxRunnningNum;
    private volatile  int clientWaitTime;
    private volatile  int fileReadBufferMin;

    public  int getFileReadBufferMin() {
        return fileReadBufferMin;
    }

    public  int getClientWaitTime() {
        return clientWaitTime;
    }

    public  int getPort() {
        return port;
    }

    public  int getMaxRunnningNum() {
        return maxRunnningNum;
    }

}
