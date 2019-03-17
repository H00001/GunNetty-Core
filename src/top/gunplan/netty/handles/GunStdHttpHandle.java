package top.gunplan.netty.handles;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.GunHttp2Response;
import top.gunplan.nio.utils.DirectoryUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 * this class need to rely on {@link GunHttp2RequestProtocl}
 */

public class GunStdHttpHandle implements GunBootServer.GunNetHandle {
    private ThreadLocal<HashMap<String, Class<? extends GunHttpMappingHandle<GunHttp2Response>>>> localUrlMapping = new ThreadLocal<>();
    private HashMap<String, Class<? extends GunHttpMappingHandle<GunHttp2Response>>> urlMapping = new HashMap<>();

    public GunStdHttpHandle(final String handlePackName) {
        ClassLoader loader = this.getClass().getClassLoader();
        List<File> classfiles;
        try {
            classfiles = DirectoryUtils.scanAllFilesFromDirectory(loader.getResource("").getPath().replace("%20", " ") + handlePackName.replace(".", "/"));
        } catch (IOException e) {
            throw new GunException(e);
        }
        assert classfiles != null;
        classfiles.forEach(classfilename -> {
            Class<? extends GunHttpMappingHandle<GunHttp2Response>> httpMapping;
            try {
                httpMapping = (Class<? extends GunHttpMappingHandle<GunHttp2Response>>) loader.loadClass(handlePackName + "." + classfilename.getName().replace(".class", ""));
                GunHttpmapping mappingRuls = httpMapping.getAnnotation(GunHttpmapping.class);
                urlMapping.put(mappingRuls.mappingRule(), httpMapping);
            } catch (ClassNotFoundException e) {
                throw new GunException(e);
            }
        });
    }

    @Override
    public void dealDataEvent(EventType t, GunBootServer.GunNettyRequestObject m) throws GunException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        localUrlMapping.set(urlMapping);
        HashMap<String, Class<? extends GunHttpMappingHandle<GunHttp2Response>>> dealmap = localUrlMapping.get();
        GunHttp2RequestProtocl req = ((GunHttp2RequestProtocl) m.requestObj().getGunRequestProtoclObject());
        Class<? extends GunHttpMappingHandle<GunHttp2Response>> dealhandel = dealmap.get(req.getRequestUrl());
        GunHttpMappingHandle<GunHttp2Response> instance = dealhandel.getConstructor().newInstance();
        m.requestObj().setSrc(instance.doResponse().serizResponse());

        //     localUrlMapping.get().
    }

    @Override
    public void dealConnEvent(EventType t, GunBootServer.GunNettyRequestObject m) throws GunException, IOException {

    }

    @Override
    public void dealCloseEvent() {

    }

    @Override
    public void dealExceptionEvent(Exception exp) {

    }

}
