package top.gunplan.netty.handles;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.netty.anno.GunHttpmapping;

import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.GunHttp2ResponseInterface;
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
    private final ThreadLocal<HashMap<String, Class<? extends GunHttpMappingHandle<GunHttp2ResponseInterface>>>> localUrlMapping = new ThreadLocal<>();
    private HashMap<String, Class<? extends GunHttpMappingHandle<GunHttp2ResponseInterface>>> urlMapping = new HashMap<>();

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
            try {
                loader.loadClass("top.gunplan.netty.anno.GunHttpmapping");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Class<? extends GunHttpMappingHandle<GunHttp2ResponseInterface>> httpMapping;
            try {
                /**
                 *
                 * warning：It could be inside class in Mappingclass with out GunHttpmapping Annotation(
                 */

                httpMapping = (Class<? extends GunHttpMappingHandle<GunHttp2ResponseInterface>>) loader.loadClass(handlePackName + "." + classfilename.getName().replace(".class", ""));
                if (httpMapping.isAnnotationPresent(GunHttpmapping.class)) {
                    urlMapping.put(httpMapping.getAnnotation(GunHttpmapping.class).mappingRule(), httpMapping);
                }

            } catch (ClassNotFoundException e) {
                throw new GunException(e);
            }
        });
    }

    @Override
    public GunNetResponseInterface dealDataEvent(GunNetRequestInterface requestInterface) throws GunException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        localUrlMapping.set(urlMapping);
        HashMap<String, Class<? extends GunHttpMappingHandle<GunHttp2ResponseInterface>>> dealmap = localUrlMapping.get();
        GunHttp2RequestProtocl request = ((GunHttp2RequestProtocl) requestInterface);
        Class<? extends GunHttpMappingHandle<GunHttp2ResponseInterface>> dealhandel = dealmap.get(request.getRequestUrl());
        GunHttpMappingHandle<GunHttp2ResponseInterface> instance = dealhandel.getConstructor().newInstance();
        return instance.doResponse(request);
        //     localUrlMapping.get().
    }

    @Override
    public GunNetResponseInterface dealConnEvent(GunNetRequestInterface requestInterface) throws GunException {
        return null;
    }

    @Override
    public void dealCloseEvent() {

    }

    @Override
    public void dealExceptionEvent(Exception exp) {
        exp.printStackTrace();
    }

}
