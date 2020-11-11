package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.hibernate.HibernateException;
import org.springframework.http.ResponseEntity;

public class PostRequestHandler<B extends RegistryModel, D extends RegistryModel, R extends RegistryModel> extends RequestHandler<B, D, R> {

    public PostRequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule) {
        super(allClasses, serializerModule);
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<String> createResponseEntity() {
        ResponseEntity<String> responseEntity = null;
        try {
            B newObject = getRequestBody();
            newObject = preProcessRequestBody(newObject);
            validateObjectByIdDoesNotExist(newObject.getId());
            getHibernateUtil().createEntityObject(getResponseBodyClass(), newObject);
            R object = (R) getHibernateUtil().readEntityObject(getResponseBodyClass(), newObject.getId());
            responseEntity = ResponseEntity.ok().body(serializeObject(object));
        } catch (HibernateException e) {
            throw new BadRequestException("Could not create " + getResponseBodyClass().getSimpleName() + ": " + e.getMessage());
        }
        return responseEntity;
    }
}