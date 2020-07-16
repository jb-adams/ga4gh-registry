package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.requestutils.ServiceRequestUtils;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;

public class PutServiceHandler extends PutRequestHandler<Implementation> {

    @Autowired
    ServiceRequestUtils serviceRequestUtils;

    public PutServiceHandler(Class<Implementation> responseClass, SerializerModuleSet serializerModuleSet, String idPathParameterName) {
        super(responseClass, serializerModuleSet, idPathParameterName);
    }

    public Implementation preProcessRequestBody(Implementation requestBody) throws ResourceNotFoundException {
        return getServiceRequestUtils().preProcessRequestBody(requestBody);
    }

    public void setServiceRequestUtils(ServiceRequestUtils serviceRequestUtils) {
        this.serviceRequestUtils = serviceRequestUtils;
    }

    public ServiceRequestUtils getServiceRequestUtils() {
        return serviceRequestUtils;
    }
}
