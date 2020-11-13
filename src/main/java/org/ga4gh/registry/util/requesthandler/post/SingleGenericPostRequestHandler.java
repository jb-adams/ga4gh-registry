package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class SingleGenericPostRequestHandler<D extends RegistryEntity> extends PostRequestHandler<D, D, D> {

    public SingleGenericPostRequestHandler(Class<D> allClasses, RegistrySerializerModule serializerModule) {
        super(allClasses, serializerModule);
    }
    
}
