# I am a README file,Please read me<br>
`Copyright © frank albert personal 2016-2018` <br>
I am a Support for high concurrency net servers，you can use me<br>
as a web services ,a load balancing and so on.<br>


```
GunBootServer server = GunBootServerFactory.getInstance();
        server.setExecutors(GunNettyExecutors.newFixedExecutorPool(10),
                GunNettyExecutors.newFixedExecutorPool(10));
        server.registerObserve(new GunNettyDefaultObserve());
        server.pipeline().addFilter(new GunNettyStdFirstFilter()).
                addFilter(new GunNettyCharsetInboundChecker()).
                setHandle(new GunNettyStringHandle());
        server.setSyncType(false);
        server.sync();
        //running time
        Thread.sleep(100000);
        server.stop();
 ```
 if you want to make it as a web server ,please use `GunStdHttp2Filter` as `GunNettyFilter` and use `GunStdHttpHandle` as `GunNettyhandle` ,
 even though you can writer the filter and headle that belong to you .
 the execute order is filter's `doRequest` method -> `handle` -> the filter's `doResponse` method.