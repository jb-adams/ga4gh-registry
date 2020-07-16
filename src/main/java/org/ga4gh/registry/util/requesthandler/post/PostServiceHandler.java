package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.requestutils.ServiceRequestUtils;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceHandler extends PostRequestHandler<Implementation> {

    @Autowired
    ServiceRequestUtils serviceRequestUtils;

    public PostServiceHandler(Class<Implementation> responseClass, SerializerModuleSet serializerModuleSet) {
        super(responseClass, serializerModuleSet);
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
