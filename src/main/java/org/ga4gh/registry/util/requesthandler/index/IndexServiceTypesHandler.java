package org.ga4gh.registry.util.requesthandler.index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class IndexServiceTypesHandler extends IndexRequestHandler<Implementation, Implementation, ServiceType> {

    public IndexServiceTypesHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, HibernateQuerier<Implementation> querier) {
        super(responseClass, serializerModule, querier);
    }

    public List<ServiceType> transformDbResults(List<Implementation> dbResults) {

        Set<ServiceType> serviceTypesSet = new HashSet<>();
        for (Implementation implementation: dbResults) {
            serviceTypesSet.add(implementation.getServiceType());
        }

        List<ServiceType> serviceTypesList = new ArrayList<>();
        for (ServiceType serviceType: serviceTypesSet) {
            serviceTypesList.add(serviceType);
        }

        return serviceTypesList;
    }
}