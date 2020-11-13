package org.ga4gh.registry.util.requesthandler.index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class IndexServiceTypesHandler extends IndexRequestHandler<Service, Service, ServiceType> {

    public IndexServiceTypesHandler(Class<Service> dbEntityClass, Class<ServiceType> responseBodyClass, RegistrySerializerModule serializerModule, HibernateQuerier<Service> querier) {
        super(dbEntityClass, responseBodyClass, serializerModule, querier);
    }

    public List<ServiceType> transformDbResults(List<Service> dbResults) {

        Set<ServiceType> serviceTypesSet = new HashSet<>();
        for (Service service: dbResults) {
            serviceTypesSet.add(service.getServiceType());
        }

        List<ServiceType> serviceTypesList = new ArrayList<>();
        for (ServiceType serviceType: serviceTypesSet) {
            serviceTypesList.add(serviceType);
        }

        return serviceTypesList;
    }
}