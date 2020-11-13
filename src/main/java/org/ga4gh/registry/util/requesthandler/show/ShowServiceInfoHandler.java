package org.ga4gh.registry.util.requesthandler.show;

import java.util.HashMap;

import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class ShowServiceInfoHandler extends SingleGenericShowRequestHandler<Service> {

    public ShowServiceInfoHandler(Class<Service> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
        setPathParams(new HashMap<String, String>());
        getPathParams().put(idPathParameterName, Ids.SERVICE_ID);
    }
}