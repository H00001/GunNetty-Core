package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;

import java.io.IOException;
import java.net.URISyntaxException;
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
public class GunGetPropertyFromBaseFile implements GunPropertyStrategy {
    private final GunNettyPropertyAnalyzier analyzier = new AbstractGunNettyStandStringPropertyAnalysiser() {
        @Override
        public void nextAnalyzing(Map<String, GunProperty> propertiesMap, String info) throws GunBootServerBase.GunNettyCanNotBootException {
            GunGetPropertyFromBaseFile.this.settingProperties0(propertiesMap, info);
        }

    };
    private String fileName = "GunNetty.conf";

    public GunGetPropertyFromBaseFile(String fileName) {
        this.fileName = fileName;
    }

    public GunGetPropertyFromBaseFile() {
    }


//    private final GunNettyPropertyAnalyzier analyzier = (propertiesMap, info) -> {
//        GunGetPropertyFromBaseFile.this.settingProperties0(propertiesMap, info);
//    };


    private boolean settingProperties0(Map<String, GunProperty> propertyMap, String filename) throws GunBootServerBase.GunNettyCanNotBootException {
        try {
            String read = Files.readString(Paths.get(Objects.
                    requireNonNull(this.getClass().getClassLoader().getResource(filename)).toURI()));
            String[] properties = read.split("\n");
            analyzier.analyzingProperties(properties, propertyMap);
        } catch (NoSuchFieldException | IllegalAccessException | IOException | URISyntaxException e) {
            throw new GunBootServerBase.GunNettyCanNotBootException(e);
        }
        return true;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) throws GunBootServerBase.GunNettyCanNotBootException {
        return settingProperties0(propertyMap, fileName);
    }


}
