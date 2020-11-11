package org.ga4gh.registry.util.requesthandler;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.InternalServerError;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.hibernate.HibernateUtil;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class RequestHandler<B extends RegistryModel, D extends RegistryModel, R extends RegistryModel> implements RequestHandlerI<B, D, R> {

    private Class<B> requestBodyClass; // the class passed on request body
    private Class<D> dbEntityClass; // the class/entity/table queried from DB
    private Class<R> responseBodyClass; // the class passed back to client
    private Map<String, String> pathParams;
    private Map<String, String> queryParams;
    private Map<String, String> headerParams;
    private B requestBody;
    private RegistrySerializerModule serializerModule;
    private String idPathParameterName;

    @Autowired
    private HibernateUtil hibernateUtil;

    /* Constructors */

    // All Generic Types are the same
    @Autowired
    @SuppressWarnings("unchecked")
    public RequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule) {
        setRequestBodyClass(allClasses);
        setDbEntityClass((Class<D>) allClasses);
        setResponseBodyClass((Class<R>) allClasses);
        setSerializerModule(serializerModule);
    }

    // Request Body and DB Query classes are shared, different response body class
    @Autowired
    @SuppressWarnings("unchecked")
    public RequestHandler(Class<B> requestBodyDbQueryClasses, Class<R> responseBodyClass, RegistrySerializerModule serializerModule) {
        setRequestBodyClass(requestBodyDbQueryClasses);
        setDbEntityClass((Class<D>) requestBodyDbQueryClasses);
        setResponseBodyClass(responseBodyClass);
        setSerializerModule(serializerModule);
    }

    // All Generic Types are different
    @Autowired
    public RequestHandler(Class<B> requestBodyClass, Class<D> dbEntityClass, Class<R> responseBodyClass, RegistrySerializerModule serializerModule) {
        setRequestBodyClass(requestBodyClass);
        setDbEntityClass(dbEntityClass);
        setResponseBodyClass(responseBodyClass);
        setSerializerModule(serializerModule);
    }

    // All Generic Types are the same
    @Autowired
    @SuppressWarnings("unchecked")
    public RequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule, String idPathParameterName) {
        setRequestBodyClass(allClasses);
        setDbEntityClass((Class<D>) allClasses);
        setResponseBodyClass((Class<R>) allClasses);
        setSerializerModule(serializerModule);
        setIdPathParameterName(idPathParameterName);
    }

    // Request Body and DB Query classes are shared, different response body class
    @Autowired
    @SuppressWarnings("unchecked")
    public RequestHandler(Class<B> requestBodyDbQueryClasses, Class<R> responseBodyClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        setRequestBodyClass(requestBodyDbQueryClasses);
        setDbEntityClass((Class<D>) requestBodyDbQueryClasses);
        setResponseBodyClass(responseBodyClass);
        setSerializerModule(serializerModule);
        setIdPathParameterName(idPathParameterName);
    }

    // All Generic Types are different
    @Autowired
    public RequestHandler(Class<B> requestBodyClass, Class<D> dbEntityClass, Class<R> responseBodyClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        setRequestBodyClass(requestBodyClass);
        setDbEntityClass(dbEntityClass);
        setResponseBodyClass(responseBodyClass);
        setSerializerModule(serializerModule);
        setIdPathParameterName(idPathParameterName);
    }

    /* Custom Methods */

    public String getIdOnPath() {
        return getPathParams().get(getIdPathParameterName());
    }

    @SuppressWarnings("unchecked")
    public D getObjectById(String id) throws BadRequestException, ResourceNotFoundException {
        return (D) getHibernateUtil().readEntityObject(getDbEntityClass(), id);
    }

    public void validateObjectByIdExists(String id) {
        D object = getObjectById(id);
        if (object == null) {
            throw new ResourceNotFoundException("no " + getEntityClassName() + " by the id: " + id.toString());
        }
    }

    public void validateObjectByIdDoesNotExist(String id) {
        D object = getObjectById(id);
        if (object != null) {
            throw new BadRequestException("another " + getEntityClassName() + " already exists by id: " + id.toString());
        }
    }

    public String serializeObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getSerializerModule());
        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerError("Internal server error: could not serialize object as JSON " + e.getMessage());
        }
        return serialized;
    }

    public String serializeObject(List<R> object) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getSerializerModule());
        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerError("Internal server error: could not serialize object as JSON " + e.getMessage());
        }
        return serialized;
    }

    public void validateRequest() throws ResourceNotFoundException {
        
    }

    public B preProcessRequestBody(B requestBody) throws ResourceNotFoundException {
        return requestBody;
    }

    public ResponseEntity<String> createResponseEntity() {
        return ResponseEntity.ok()
            .body("");
    }

    private String getEntityClassName() {
        return getDbEntityClass().getSimpleName();
    }

    /* Setters and Getters */

    public void setRequestBodyClass(Class<B> requestBodyClass) {
        this.requestBodyClass = requestBodyClass;
    }

    public Class<B> getRequestBodyClass() {
        return requestBodyClass;
    }

    public void setDbEntityClass(Class<D> dbEntityClass) {
        this.dbEntityClass = dbEntityClass;
    }

    public Class<D> getDbEntityClass() {
        return dbEntityClass;
    }

    public void setResponseBodyClass(Class<R> responseBodyClass) {
        this.responseBodyClass = responseBodyClass;
    }

    public Class<R> getResponseBodyClass() {
        return responseBodyClass;
    }

    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams = pathParams;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setRequestBody(B requestBody) {
        this.requestBody = requestBody;
    }

    public B getRequestBody() {
        return requestBody;
    }

    public void setSerializerModule(RegistrySerializerModule serializerModule) {
        this.serializerModule = serializerModule;
    }

    public RegistrySerializerModule getSerializerModule() {
        return serializerModule;
    }

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public HibernateUtil getHibernateUtil() {
        return hibernateUtil;
    }
}
