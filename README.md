# I am a README file,Please read me<br>
`Copyright © frank albert personal 2016-2018` <br>
I am a Support for high concurrency net servers，you can use me<br>
as a web services ,a load balancing and so on.<br>


```
GunBootServer server = GunBootServerFactory.getInstance();
        ExecutorService es0 = new ThreadPoolExecutor(100, 1000,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        ExecutorService es1 = new ThreadPoolExecutor(100, 1000,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        server.setExecuters(es0, es1).getPipeline().addFilter(new GunNettyStdFirstFilter()).
                addFilter(new GunStdHttp2Filter()).
              //  addFilter(new GunHttpdHostCheck()).
                setHandle(new GunStdHttpHandle("top.gunplan.netty.test"));
        try {
            server.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
 ```
 if you want to make it as a web server ,please use `GunStdHttp2Filter` as `GunNettyFilter` and use `GunStdHttpHandle` as `GunNettyhandle` ,
 even though you can writer the filter and headle that belong to you .
 the execute order is filter's `doRequest` method -> `handle` -> the filter's `doResponse` method.