package top.gunplan.netty.impl

class GunNettySupportParameter {

    companion object {
        private var port: Int = 8822
        private var maxThread: Int = Runtime.getRuntime().availableProcessors()
        fun getPort(): Int {
            return port
        }


    }

}
