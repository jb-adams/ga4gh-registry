package org.ga4gh.registry.util.requesthandler;

import java.util.List;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.model.RegistryModel;
import org.springframework.http.ResponseEntity;

public interface RequestHandlerI<B extends RegistryModel, D extends RegistryEntity, R extends RegistryModel> {
    public B preProcessRequestBody(B requestBody) throws ResourceNotFoundException;
    public List<R> transformDbResults(List<D> dbResults);
    public ResponseEntity<String> createResponseEntity();
}
