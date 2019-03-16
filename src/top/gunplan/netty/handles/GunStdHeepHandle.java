package top.gunplan.netty.handles;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.filters.protocls.GunHttp2Protocl;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.handles.http.GunHttpResponse;
import top.gunplan.nio.utils.DirectoryUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * this class need to rely on {@link GunHttp2Protocl}
 */

public class GunStdHeepHandle implements GunBootServer.GunNetHandle {
    private ThreadLocal<HashMap<String, Class<? extends GunHttpMappingHandle<GunHttpResponse>>>> localUrlMapping = new ThreadLocal<>();
    private HashMap<String, Class<? extends GunHttpMappingHandle<GunHttpResponse>>> urlMapping = new HashMap<>();


    public GunStdHeepHandle(final String handlePackName) {
        ClassLoader loader = this.getClass().getClassLoader();
        List<File> classfiles;
        try {
            classfiles = DirectoryUtils.scanAllFilesFromDirectory(loader.getResource("").getPath().replace("%20", " ") + handlePackName.replace(".", "/"));
        } catch (IOException e) {
            throw new GunException(e);
        }
        assert classfiles != null;
        classfiles.forEach(classfilename -> {
            Class<? extends GunHttpMappingHandle<GunHttpResponse>> httpMapping;
            try {
                httpMapping = (Class<? extends GunHttpMappingHandle<GunHttpResponse>>) loader.loadClass(handlePackName + "." + classfilename.getName().replace(".class", ""));
                GunHttpmapping mappingRuls = httpMapping.getAnnotation(GunHttpmapping.class);
                urlMapping.put(mappingRuls.mappingRule(), httpMapping);
            } catch (ClassNotFoundException e) {
                throw new GunException(e);
            }
        });
    }

    @Override
    public void dealDataEvent(EventType t, GunBootServer.GunNettyRequestObject m) throws GunException, IOException {
        localUrlMapping.set(urlMapping);
        ((GunHttp2Protocl) m.requestObj().getGunRequestProtoclObject()).getRequstHead();
   //     localUrlMapping.get().
    }

    @Override
    public void dealConnEvent(EventType t, GunBootServer.GunNettyRequestObject m) throws GunException, IOException {

    }

    @Override
    public void dealCloseEvent(EventType t) throws GunException {

    }

    @Override
    public void dealExceptionEvent(EventType t) {

    }
}
