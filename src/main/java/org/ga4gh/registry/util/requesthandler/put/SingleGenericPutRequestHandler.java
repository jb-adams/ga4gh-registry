package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class SingleGenericPutRequestHandler<D extends RegistryEntity> extends PutRequestHandler<D, D, D> {

    public SingleGenericPutRequestHandler(Class<D> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }


    
}
