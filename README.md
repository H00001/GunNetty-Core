# I am a README file, please read me first.
`Copyright © frank albert personal 2016-2018`  
This is a support for high concurrency net servers. You can use me  
as a web service, a load balancing service and etc.  

## GETTING START
**As a old ferric double click 666 game server**
```Java
       // set property strategy
        GunNettySystemService.PROPERTY_MANAGER.setStrategy(new GunGetPropertyFromBaseFile());
       // get a server instance 
        GunBootServer server = GunBootServerFactory.newInstance();
        server
               // set sum of thread          
                .setExecutors(10, 10)
               // use steal work model (ForkJoinPool)
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
       // running doTime
        Thread.sleep(100);
        System.out.println(GunBootServer.GunNettyWorkState.getState(server.stop()));
 ```
Yep. The next is playing the game!
```bash
    telent [::]:1 8822
    please double click 666
    > 666
    you have times: 10
...
```
### Building the Application
If you want to install it on the local, please execute the following.
```shell script
mvn clean && mvn install to install this project
```
## CREATE YOUR SELF SERVICE
If you want to make it as a web server, please use `GunStdHttp2Filter` as `GunNettyFilter` and  
use `GunStdHttpHandle` as `GunNettyhandle`,even though you can writer the filter and handle that   
belong to you.    
The execute order is `doRequest` of filters method -> `handle` -> `doResponse` of filters method.
 
 
