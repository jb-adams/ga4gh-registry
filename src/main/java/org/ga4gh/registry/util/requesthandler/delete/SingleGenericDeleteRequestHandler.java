package org.ga4gh.registry.util.requesthandler.delete;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class SingleGenericDeleteRequestHandler<D extends RegistryEntity> extends DeleteRequestHandler<D, D, D> {

    public SingleGenericDeleteRequestHandler(Class<D> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }
}
