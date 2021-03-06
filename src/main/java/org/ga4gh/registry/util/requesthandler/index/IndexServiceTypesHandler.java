package org.ga4gh.registry.util.requesthandler.index;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class IndexServiceTypesHandler extends IndexRequestHandler<Implementation> {

    public IndexServiceTypesHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, HibernateQuerier<Implementation> querier) {
        super(responseClass, serializerModule, querier);
    }

    public ResponseEntity<String> createResponseEntity() {
        List<Implementation> implementations = getResultsFromDb();
        Set<ServiceType> serviceTypes = postProcessResults(implementations);
        String serialized = serializeObject(serviceTypes);
        return ResponseEntity.ok().body(serialized);
    }

    public Set<ServiceType> postProcessResults(List<Implementation> implementations) {

        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        return serviceTypes;
    }
}