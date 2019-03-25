#I am a README file,Please read me<br>
`Copyright © frank albert personal 2016-2018` <br>
I am a Support for high concurrency net servers，you can use me<br>
as a web services ,a load balancing and so on.<br>




``public class GunTestJunit {

    @Test\
    public void doTest() throws Exception {\
        GunBootServer server = GunBootServerFactory.getInstance();\
        server.setExecuters(Executors.newFixedThreadPool(10), Executors.newFixedThreadPool(10)).addFilter(new GunStdHttp2Filter()).setHandel(new GunStdHttpHandle("top.gunplan.netty.test"));
        server.sync();
 }```