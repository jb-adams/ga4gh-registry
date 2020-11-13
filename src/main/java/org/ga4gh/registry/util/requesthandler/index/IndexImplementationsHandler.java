package org.ga4gh.registry.util.requesthandler.index;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class IndexImplementationsHandler extends SingleGenericIndexRequestHandler<Implementation> {

    public IndexImplementationsHandler(Class<Implementation> allClasses, RegistrySerializerModule serializerModule, HibernateQuerier<Implementation> querier) {
        super(allClasses, serializerModule, querier);
    }
}