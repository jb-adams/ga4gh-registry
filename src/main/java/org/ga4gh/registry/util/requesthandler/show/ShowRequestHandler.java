package org.ga4gh.registry.util.requesthandler.show;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class ShowRequestHandler<B extends RegistryModel, D extends RegistryModel, R extends RegistryModel> extends RequestHandler<B, D, R> {

    public ShowRequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        validateObjectByIdExists(getIdOnPath());
        validateRequest();
        return ResponseEntity.ok().body(serializeObject(getObjectById(getIdOnPath())));
    }
}