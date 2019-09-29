/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyStringUtil;
import top.gunplan.netty.impl.property.base.PropertyDataBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * GunGetPropertyFromBaseFile
 *
 * @author frank albert
 * @version 0.0.0.2
 * # 2019-06-25 20:55
 */
public class GunGetPropertyFromBaseFile implements GunPropertyStrategy, PropertyDataBuilder<String[]> {
    private final GunNettyPropertyAnalyzer<String, String[]> analyser = new AbstractGunNettyStandStringPropertyAnalyizer() {
        @Override
        public void nextAnalyze(Map<String, GunProperty> propertiesMap, String info) throws GunBootServerBase.GunNettyCanNotBootException {
            GunGetPropertyFromBaseFile.this.settingProperties0(propertiesMap, info);
        }

    };
    private String fileName = "GunNetty.conf";


    public GunGetPropertyFromBaseFile(String fileName) {
        this.fileName = fileName;
    }

    public GunGetPropertyFromBaseFile() {
    }

    private boolean settingProperties0(Map<String, GunProperty> propertyMap, String filename) throws GunBootServerBase.GunNettyCanNotBootException {
        try {
            String[] properties = GunNettyStringUtil.confRead(filename).split("\n");
            analyser.analyzingProperties(properties, propertyMap);
        } catch (NullPointerException | NoSuchFieldException | IllegalAccessException | IOException | URISyntaxException fileNotFound) {
            throw new GunBootServerBase.GunNettyCanNotBootException(fileNotFound);
        }
        return true;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) throws GunBootServerBase.GunNettyCanNotBootException {
        return settingProperties0(propertyMap, fileName);
    }


    @Override
    public String[] create() throws IOException {
//        final String read = Files.readString(Paths.get((Objects.requireNonNull(
//                this.getClass().getClassLoader().getResource(fileName)).getPath())));
//        return read.split("\n");
        return null;
    }
}
