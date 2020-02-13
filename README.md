# I am a README file,Please read me<br>
`Copyright © frank albert personal 2016-2018` <br>
I am a Support for high concurrency net servers. you can use me<br>
as a web service, a load balancing service and so on.<br>

## GETTING START
### As a old ferric double click 666 game server
```Java
        //set property strategy
        GunNettySystemService.PROPERTY_MANAGER.setStrategy(new GunGetPropertyFromBaseFile());
        //get a server instance 
        GunBootServer server = GunBootServerFactory.newInstance();
        server
                //set sum of thread          
                .setExecutors(10, 10)
                //use steal work model (ForkJoinPool)
                .useStealMode(true)
                .registerObserve(new GunNettyDefaultObserve())
                .onHasChannel(pipeline -> pipeline
                        .setMetaInfoChangeObserver(new DefaultGunNettyChildrenPipelineChangedObserve())
                        .addDataFilter(new GunNettyStdFirstFilter().setObserve(null))
                        .addDataFilter(new GunNettyCharsetInboundChecker())
                        .addConnFilter(new GunNettyStdFirstFilter())
                        .addDataFilter(new GunNettyExampleStopFilter())
                        .setHandle((GunNettyChildrenHandle) new GunNettyStringHandle())
                        .setHandle((GunNettyParentHandle) new GunNettyStringHandle())
                        .addNettyTimer(new GunTimerExample()));
        server.timeManager().addGlobalTimers(new GlobalTimer());
        server.setSyncType(false);
        Assertions.assertEquals(server.sync(), GunBootServer.GunNettyWorkState.ASYNC.state |
                GunBootServer.GunNettyWorkState.RUNNING.state);
        //running doTime
        Thread.sleep(100);
        System.out.println(GunBootServer.GunNettyWorkState.getState(server.stop()));
 ```
Yep. That's it. You should see the following output:
    please double click 666
### Building the Application
if you want to install it on the local, please execute
```shell script
mvn clean && mvn install to install this project
```
## CREATE YOUR SELF ERVICE
 if you want to make it as a web server, please use `GunStdHttp2Filter` as `GunNettyFilter` and use `GunStdHttpHandle` as `GunNettyhandle`,
 even though you can writer the filter and headle that belong to you.  
 the execute order is filter's `doRequest` method -> `handle` -> the filter's `doResponse` method.
