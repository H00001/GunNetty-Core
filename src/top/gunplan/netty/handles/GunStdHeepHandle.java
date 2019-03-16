package top.gunplan.netty.handles;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.filters.protocls.GunHttpProtocl;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.handles.http.GunHttpResponse;
import top.gunplan.nio.utils.DirectoryUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * this class need to rely on {@link top.gunplan.netty.filters.protocls.GunHttpProtocl}
 */

public class GunStdHeepHandle implements GunBootServer.GunNetHandle {
    private ThreadLocal<HashMap<String, Class<? extends GunHttpMappingHandle<GunHttpResponse>>>> localUrlMapping = new ThreadLocal<>();
    private HashMap<String, Class<? extends GunHttpMappingHandle<GunHttpResponse>>> urlMapping = new HashMap<>();


    public GunStdHeepHandle(final String handlePackName) {
        ClassLoader loader = this.getClass().getClassLoader();

        //loader.getResource("").toString().replace("%20"," ") + handlePackName.replace(".", "/")
        List<File> classfiles = null;
        try {
            classfiles = DirectoryUtils.getAllFilesFromDirectory(loader.getResource("").getPath().replace("%20", " ") + handlePackName.replace(".", "/"));
        } catch (IOException e) {
            e.printStackTrace();
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
    public void dealevent(EventType t, GunBootServer.GunNettyRequestObject m) throws GunException, IOException {
        localUrlMapping.set(urlMapping);
        ((GunHttpProtocl) m.requestObj().getGunRequestProtoclObject()).getRequstHead();

    }
}
