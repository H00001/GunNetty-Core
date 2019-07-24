package top.gunplan.netty.impl.eventloop.aio;


import top.gunplan.netty.impl.eventloop.AbstractGunCoreAioEventLoop;

/**
 * GunCoreAioConnectionEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-09 10:35
 */
public class GunCoreAioConnectionEventLoopImpl extends AbstractGunCoreAioEventLoop {
//    private final AsynchronousServerSocketChannel var57;
//    private AsynchronousChannelProvider provider = new BsdAsynchronousChannelProvider();
//    private GunNettyPipeline pipeline;
//
//    GunCoreAioConnectionEventLoopImpl(ExecutorService deal, GunNettyPipeline dealHandle, int port) throws IOException {
//        this.pipeline = dealHandle;
//        var57 = provider.openAsynchronousServerSocketChannel(provider.openAsynchronousChannelGroup(deal, 10));
//        try {
//            var57.bind(new InetSocketAddress(port));
//        } catch (IOException e) {
//            AbstractGunBaseLogUtil.urgency(e.getMessage());
//        }
//
//    }

    @Override
    public synchronized void run() {

//        try {
//           var57.accept()
//
//        } catch (Exception exp) {
//            AbstractGunBaseLogUtil.error(exp.getMessage());
//        }


    }

    @Override
    public void loop() {

    }

    @Override
    public void startEventLoop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void stopEventLoop() {

    }


}
