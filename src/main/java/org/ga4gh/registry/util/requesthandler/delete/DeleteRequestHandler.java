package org.ga4gh.registry.util.requesthandler.delete;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class DeleteRequestHandler<B extends RegistryModel, D extends RegistryEntity, R extends RegistryModel> extends RequestHandler<B, D, R> {

    public DeleteRequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        validateObjectByIdExists(getIdOnPath());
        validateRequest();
        getHibernateUtil().deleteEntityObject(getResponseBodyClass(), getIdOnPath());
        return ResponseEntity.ok().body("");
    }
}
