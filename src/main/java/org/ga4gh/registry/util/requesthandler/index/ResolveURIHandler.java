package org.ga4gh.registry.util.requesthandler.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Curie;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.URIResolution;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.ga4gh.registry.util.uriresolver.URIResolver;
import org.springframework.http.ResponseEntity;

public class ResolveURIHandler extends IndexRequestHandler<Service, Service, URIResolution> {

    private Curie curie;

    public ResolveURIHandler(Class<Service> requestBodyDbEntityClass, Class<URIResolution> responseBodyClass, RegistrySerializerModule serializerModule, HibernateQuerier<Service> querier) {
        super(requestBodyDbEntityClass, responseBodyClass, serializerModule, querier);
    }

    public void customizeQueryBuilder(HibernateQueryBuilder qb) {
        try {
            Curie curie = Curie.fromString(getPathParams().get("uri"));
            setCurie(curie);
        }  catch (ParseException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        qb.filter("curiePrefix", getCurie().getPrefix());
    }

    public ResponseEntity<String> createResponseEntity() {
        Service service = getSingleResultFromDb();
        if (service == null) {
            throw new ResourceNotFoundException("There is no registered service with the CURIE prefix: '" + curie.getPrefix() + "'");
        }
        URIResolution response = resolveURLForService(service, getCurie().getId());
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body(serializeObject(response));
        return responseEntity;
    }

    private URIResolution resolveURLForService(Service service, String id) {
        URIResolution resolution = new URIResolution();

        Map<String, String> resolutionMethodByServiceType = new HashMap<>();
        resolutionMethodByServiceType.put("org.ga4gh:drs:1.0.0", "drsv1");
        resolutionMethodByServiceType.put("org.ga4gh:drs:1.1.0", "drsv1");

        try {
            String methodName = resolutionMethodByServiceType.get(service.getServiceType().signature());
            URIResolver resolver = new URIResolver();
            Method method = resolver.getClass().getMethod(methodName, Service.class, id.getClass());
            String resolvedURL = (String) method.invoke(resolver, service, id);
            resolution.setServiceId(service.getId());
            resolution.setServiceName(service.getName());
            resolution.setServiceType(service.getServiceType());
            resolution.setResolvedURL(resolvedURL);
            
        } catch (NoSuchMethodException ex) {
            throw new BadRequestException("There is no URI resolution method for this service type");
        } catch (IllegalAccessException ex) {
            throw new BadRequestException("Could not resolve URI");
        } catch (InvocationTargetException ex) {
            throw new BadRequestException("Could not resolve URI");
        }
        
        return resolution;
    }

    /* setters and getters */

    public void setCurie(Curie curie) {
        this.curie = curie;
    }

    public Curie getCurie() {
        return curie;
    }
}