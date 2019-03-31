package top.gunplan.netty.handles;


import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNetHandle;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.AbstractGunHttp2Response;
import top.gunplan.nio.utils.AbstractGunBaseLogUtil;
import top.gunplan.nio.utils.GunDirectoryUtil;
import top.gunplan.nio.utils.GunStringUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 * this class need to rely on {@link GunHttp2RequestProtocl}
 *
 * @author dosdrtt
 */

public class GunStdHttpHandle implements GunNetHandle {
    private final ThreadLocal<HashMap<String, GunHttpMappingHandle<AbstractGunHttp2Response>>> localUrlMappingObject = new ThreadLocal<>();
    private final ThreadLocal<HashMap<String, Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>>> localUrlMapping = new ThreadLocal<>();
    private HashMap<String, Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>> urlMapping = new HashMap<>();
    private HashMap<String, GunHttpMappingHandle<AbstractGunHttp2Response>> urlMappingObject = new HashMap<>();

    public GunStdHttpHandle(final String handlePackName) {
        ClassLoader loader = this.getClass().getClassLoader();
        List<GunDirectoryUtil.GunHttpMappingFileReference> classfiles;
        try {
            classfiles = GunDirectoryUtil.scanAllFilesFromDirectory(loader.getResource("").getPath().replace("%20", " ") + handlePackName.replace(".", "/"));
        } catch (IOException e) {
            throw new GunException(e);
        }
        assert classfiles != null;
        classfiles.forEach(classname -> {
            Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>> httpMapping;
            try {
                /**
                 *
                 * warning：It could be inside class in Mappingclass with out GunHttpmapping Annotation(
                 */

                httpMapping = (Class<? extends GunHttpMappingHandle<AbstractGunHttp2Response>>) loader.loadClass(handlePackName + classname.getBase() + classname.getClcasfile().getName().replace(".class", ""));
                if (httpMapping.isAnnotationPresent(GunHttpmapping.class)) {
                    urlMapping.put(httpMapping.getAnnotation(GunHttpmapping.class).mappingRule(), httpMapping);
                }

            } catch (ClassNotFoundException e) {
                throw new GunException(e);
            }
        });
    }

    @Override
    public GunNetResponseInterface dealDataEvent(GunNetRequestInterface requestInterface) throws GunException {
        localUrlMappingObject.set(urlMappingObject);
        localUrlMapping.set(urlMapping);
        GunHttp2RequestProtocl request = ((GunHttp2RequestProtocl) requestInterface);
        AbstractGunBaseLogUtil.debug("request:" + request.getRequestUrl(), "[CONNECTION][HTTP]");
        GunHttpMappingHandle<AbstractGunHttp2Response> runner = null;
        try {
            runner = findHandelandRun(request.getRequestUrl());
        } catch (Exception exp) {
            AbstractGunBaseLogUtil.error(exp.getMessage());
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
        return dealhandel.getConstructor().newInstance();
    }


    @Override
    public GunNetResponseInterface dealConnEvent(GunNetRequestInterface requestInterface) throws GunException {
        return null;
    }

    @Override
    public void dealCloseEvent() {
        AbstractGunBaseLogUtil.urgency("CLOSED");
    }

    @Override
    public void dealExceptionEvent(Exception exp) {
        AbstractGunBaseLogUtil.urgency("CLOSED");
        exp.printStackTrace();
    }

}
