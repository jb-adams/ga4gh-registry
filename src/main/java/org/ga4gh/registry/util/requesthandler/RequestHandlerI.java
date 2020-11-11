package org.ga4gh.registry.util.requesthandler;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryModel;
import org.springframework.http.ResponseEntity;

public interface RequestHandlerI<B extends RegistryModel, D extends RegistryModel, R extends RegistryModel> {
    public B preProcessRequestBody(B requestBody) throws ResourceNotFoundException;
    public ResponseEntity<String> createResponseEntity();
}
