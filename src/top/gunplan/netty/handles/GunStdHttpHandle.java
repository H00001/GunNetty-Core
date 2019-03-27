package top.gunplan.netty.handles;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.netty.anno.GunHttpmapping;

import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.AbstractGunHttp2Response;
import top.gunplan.nio.utils.GunBaseLogUtil;
import top.gunplan.nio.utils.GunDirectoryUtil;
import top.gunplan.nio.utils.GunStringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 * this class need to rely on {@link GunHttp2RequestProtocl}
 */

public class GunStdHttpHandle implements GunBootServer.GunNetHandle {

    {
        GunBaseLogUtil.setLevel(0);
    }
    private final ThreadLocal<HashMap<String, Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>>> localUrlMapping = new ThreadLocal<>();
    private HashMap<String, Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>> urlMapping = new HashMap<>();

    public GunStdHttpHandle(final String handlePackName) {
        ClassLoader loader = this.getClass().getClassLoader();
        List<GunDirectoryUtil.GunHttpMappingFileReference> classfiles;
        try {
            classfiles = GunDirectoryUtil.scanAllFilesFromDirectory(loader.getResource("").getPath().replace("%20", " ") + handlePackName.replace(".", "/"));
        } catch (IOException e) {
            throw new GunException(e);
        }
        assert classfiles != null;
        classfiles.forEach(classfilename -> {
            Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>> httpMapping;
            try {
                /**
                 *
                 * warning：It could be inside class in Mappingclass with out GunHttpmapping Annotation(
                 */

                httpMapping = (Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>) loader.loadClass(handlePackName + classfilename.getBase() + classfilename.getClcasfile().getName().replace(".class", ""));
                if (httpMapping.isAnnotationPresent(GunHttpmapping.class)) {
                    urlMapping.put(httpMapping.getAnnotation(GunHttpmapping.class).mappingRule(), httpMapping);
                }

            } catch (ClassNotFoundException e) {
                throw new GunException(e);
            }
        });
    }

    @Override
    public GunNetResponseInterface dealDataEvent(GunNetRequestInterface requestInterface) throws GunException{

        localUrlMapping.set(urlMapping);
        GunHttp2RequestProtocl request = ((GunHttp2RequestProtocl) requestInterface);
        GunBaseLogUtil.info("request:"+request.getRequestUrl());
        GunHttpMappingHandle<AbstractGunHttp2Response> runner = null;
        try {
            runner = findHandelandRun(request.getRequestUrl());
        } catch (Exception exp) {
            GunBaseLogUtil.error(exp.getMessage());
        }
        assert runner != null;
        return runner.doResponse(request);
        //     localUrlMapping.get().
    }

    private GunHttpMappingHandle<AbstractGunHttp2Response> findHandelandRun(String requestUrl) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        HashMap<String, Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>> dealmap = localUrlMapping.get();
        Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>> dealhandel = dealmap.get(requestUrl);
        while (dealhandel == null) {
            requestUrl = GunStringUtil.removeLastUrl(requestUrl);
            dealhandel = dealmap.get(requestUrl + "*");
            if ("/".equals(requestUrl) && dealhandel == null) {
                throw new GunException("404 or 404 pages not found");
            }
        }
        GunHttpMappingHandle<AbstractGunHttp2Response> instance = dealhandel.getConstructor().newInstance();
        return instance;
    }


    @Override
    public GunNetResponseInterface dealConnEvent(GunNetRequestInterface requestInterface) throws GunException {
        return null;
    }

    @Override
    public void dealCloseEvent() {
        GunBaseLogUtil.urgency("CLOSED");
    }

    @Override
    public void dealExceptionEvent(Exception exp) {
        GunBaseLogUtil.urgency("CLOSED");
        exp.printStackTrace();
    }

}
