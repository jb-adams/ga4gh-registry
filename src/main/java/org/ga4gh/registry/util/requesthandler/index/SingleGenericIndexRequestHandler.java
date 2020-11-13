package org.ga4gh.registry.util.requesthandler.index;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class SingleGenericIndexRequestHandler<D extends RegistryEntity> extends IndexRequestHandler<D, D, D> {

    public SingleGenericIndexRequestHandler(Class<D> allClasses, RegistrySerializerModule serializerModule, HibernateQuerier<D> querier) {
        super(allClasses, serializerModule, querier);
    }
}
