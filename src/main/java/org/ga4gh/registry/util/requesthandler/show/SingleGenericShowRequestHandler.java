package org.ga4gh.registry.util.requesthandler.show;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class SingleGenericShowRequestHandler<D extends RegistryEntity> extends ShowRequestHandler<D, D, D> {

    public SingleGenericShowRequestHandler(Class<D> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }
    
}
