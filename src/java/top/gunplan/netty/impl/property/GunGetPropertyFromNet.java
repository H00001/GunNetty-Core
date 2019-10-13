/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyStringUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * GunGetPropertyFromNet
 *
 * @author frank albert
 * @version 0.0.0.3
 */

public class GunGetPropertyFromNet implements GunPropertyStrategy {

    private String address;
    private GunNettyPropertyExporter exporter = new GunNettyPropertyExporter() {
    };

    private GunNettyPropertyAnalyzer<String, String[]> analyzer =
            new AbstractGunNettyStandStringPropertyAnalyizer() {
                @Override
                public void nextAnalyze(Map<String, GunProperty> propertiesMap, String info)
                        throws GunBootServerBase.GunNettyCanNotBootException {
                    GunGetPropertyFromNet.this.address = info;
                    GunGetPropertyFromNet.this.settingProperties(propertiesMap);
                }
            };


    public GunGetPropertyFromNet(String address) {
        this.address = address;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) {
        assert propertyMap != null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        exporter.export("acquire configure from :" + address);
        client.sendAsync(request, HttpResponse.BodyHandlers.
                ofString())
                .whenCompleteAsync((body, en) -> {
                    if (body.statusCode() == 200) {
                        assert GunNettyStringUtil.isNotEmpty0(body.body());
                        if (en == null) {
                            try {
                                analyzer.analyzingProperties(body.body().split("\n"), propertyMap);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                throw new GunBootServerBase.GunNettyCanNotBootException(e);
                            }
                        } else {
                            throw new GunBootServerBase.GunNettyCanNotBootException(en);
                        }
                    } else {
                        throw new GunBootServerBase.GunNettyCanNotBootException("status error");
                    }
                })
                .join();


        return true;
    }
}
