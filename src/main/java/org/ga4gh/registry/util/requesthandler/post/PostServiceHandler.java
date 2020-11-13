package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.util.requesthandler.utils.ImplementationRequestUtils;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceHandler extends SingleGenericPostRequestHandler<Service> {

    @Autowired
    ImplementationRequestUtils implementationRequestUtils;

    public PostServiceHandler(Class<Service> responseClass, RegistrySerializerModule serializerModule) {
        super(responseClass, serializerModule);
    }

    @Override
    public Service preProcessRequestBody(Service requestBody) throws ResourceNotFoundException {
        return getImplementationRequestUtils().preProcessService(requestBody);
    }

    public void setImplementationRequestUtils(ImplementationRequestUtils implementationRequestUtils) {
        this.implementationRequestUtils = implementationRequestUtils;
    }

    public ImplementationRequestUtils getImplementationRequestUtils() {
        return implementationRequestUtils;
    }
}
