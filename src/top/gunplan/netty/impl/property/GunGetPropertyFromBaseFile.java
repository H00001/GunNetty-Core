/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.impl.property.base.PropertyDataBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * GunGetPropertyFromBaseFile
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-25 20:55
 */
public class GunGetPropertyFromBaseFile implements GunPropertyStrategy, PropertyDataBuilder<String[]> {
    private final GunNettyPropertyAnalyzer<String, String[]> analyzier = new AbstractGunNettyStandStringPropertyAnalysiser() {
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
            final String read = Files.readString(Paths.get((Objects.requireNonNull(
                    this.getClass().getClassLoader().getResource(filename)).getPath())));
            String[] properties = read.split("\n");
            analyzier.analyzingProperties(properties, propertyMap);
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            throw new GunBootServerBase.GunNettyCanNotBootException(e);
        }
        return true;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) throws GunBootServerBase.GunNettyCanNotBootException {
        return settingProperties0(propertyMap, fileName);
    }


    @Override
    public String[] create() throws IOException {
        final String read = Files.readString(Paths.get((Objects.requireNonNull(
                this.getClass().getClassLoader().getResource(fileName)).getPath())));
        return read.split("\n");
    }
}
