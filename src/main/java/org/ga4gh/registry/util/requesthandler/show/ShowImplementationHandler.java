package org.ga4gh.registry.util.requesthandler.show;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class ShowImplementationHandler extends ShowRequestHandler<Implementation> {

    public ShowImplementationHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public void validateRequest() throws ResourceNotFoundException {
        Implementation service = getObjectById(getIdOnPath());
        if (!service.getCategory().equals(ImplementationCategory.implementation)) {
            throw new ResourceNotFoundException("No implementation with id: " + getIdOnPath());
        }
    }
}