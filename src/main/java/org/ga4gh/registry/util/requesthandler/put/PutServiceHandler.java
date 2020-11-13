package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.util.requesthandler.utils.ImplementationRequestUtils;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;

public class PutServiceHandler extends SingleGenericPutRequestHandler<Service> {

    @Autowired
    ImplementationRequestUtils implementationRequestUtils;

    public PutServiceHandler(Class<Service> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }

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
