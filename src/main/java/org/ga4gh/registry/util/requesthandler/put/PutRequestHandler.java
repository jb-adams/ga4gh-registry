package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class PutRequestHandler<B extends RegistryModel, D extends RegistryModel, R extends RegistryModel> extends RequestHandler<B, D, R> {

    public PutRequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(allClasses, serializerModule, idPathParameterName);
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<String> createResponseEntity() {
        B newObject = getRequestBody();
        newObject = preProcessRequestBody(newObject);
        String oldId = getIdOnPath();
        String newId = newObject.getId();
        validateObjectByIdExists(oldId);
        if (!oldId.equals(newId)) {
            validateObjectByIdDoesNotExist(newId);
        }
        getHibernateUtil().updateEntityObject(getResponseBodyClass(), oldId, newId, newObject);
        R finalObject = (R) getHibernateUtil().readEntityObject(getResponseBodyClass(), newId);
        return ResponseEntity.ok().body(serializeObject(finalObject));
    }
}
