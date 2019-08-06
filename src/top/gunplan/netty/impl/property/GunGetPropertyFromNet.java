/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * GunGetPropertyFromNet
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-06 08:31
 */

public class GunGetPropertyFromNet implements GunPropertyStrategy {

    private String address;
    private GunNettyPropertyExporter exporter = new GunNettyPropertyExporter() {
    };

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


        exporter.export("acquire configure from :" + address);
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .whenCompleteAsync((body, en) -> {
                    if (en == null) {
                        try {
                            analyzier.analyzingProperties(body.split("\n"), propertyMap);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new GunBootServerBase.GunNettyCanNotBootException(e);
                        }
                    } else {
                        throw new GunBootServerBase.GunNettyCanNotBootException(en);
                    }
                })
                .join();

        return true;
    }
}
