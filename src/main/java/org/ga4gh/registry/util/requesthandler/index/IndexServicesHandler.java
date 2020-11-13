package org.ga4gh.registry.util.requesthandler.index;

import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class IndexServicesHandler extends SingleGenericIndexRequestHandler<Service> {

    public IndexServicesHandler(Class<Service> allClasses, RegistrySerializerModule serializerModule, HibernateQuerier<Service> querier) {
        super(allClasses, serializerModule, querier);
    }

    @Override
    public void customizeQueryBuilder(HibernateQueryBuilder qb) {
        addTypeFilter();
    }

    public void addTypeFilter() {
        String type = getQueryParams().get("type");
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            HibernateQueryBuilder qb = getQueryBuilder();
            if (!serviceType.getArtifact().equals("*")) {
                qb.filter("standardVersion.standard.artifact", serviceType.getArtifact());  
            }
            if (!serviceType.getVersion().equals("*")) {
                qb.filter("standardVersion.versionNumber", serviceType.getVersion());
            }
        }
    }
}