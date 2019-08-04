package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class GunGetPropertyFromNet implements GunPropertyStrategy {

    private String address;

    private GunNettyPropertyAnalyzier analyzier = new AbstractGunNettyStandStringPropertyAnalysiser() {
        @Override
        public void nextAnalyzing(Map<String, GunProperty> propertiesMap, String info) throws GunBootServerBase.GunNettyCanNotBootException {
            GunGetPropertyFromNet.this.address = info;
            GunGetPropertyFromNet.this.settingProperties(propertiesMap);
        }
    };


    public GunGetPropertyFromNet(String address) {
        this.address = address;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();


        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(f -> {
                    GunNettyContext.logger.error(f.getCause());
                    return null;
                })
                .thenAccept(body -> {
                    // FIXME: 2019-08-03
                    //todo
                    try {
                        analyzier.analyzingProperties(body.split("\n"), propertyMap);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new GunBootServerBase.GunNettyCanNotBootException(e);
                    }
                })
                .join();

        return true;
    }
}
