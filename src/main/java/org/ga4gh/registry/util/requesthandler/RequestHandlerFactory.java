package org.ga4gh.registry.util.requesthandler;

import java.util.Map;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.model.RegistryModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public class RequestHandlerFactory<B extends RegistryModel, D extends RegistryEntity, R extends RegistryModel> implements RequestHandlerFactoryI<B> {

    private ApplicationContext context;
    private Class<B> requestBodyClass; // the class passed on request body
    private Class<D> dbEntityClass; // the class/entity/table queried from DB
    private Class<R> responseBodyClass; // the class passed back to client
    private String requestHandlerBeanName;
    
    /* Constructor */

    @SuppressWarnings("unchecked")
    public RequestHandlerFactory(Class<B> allClasses, String requestHandlerBeanName) {
        setRequestBodyClass(allClasses);
        setDbEntityClass((Class<D>) allClasses);
        setResponseBodyClass((Class<R>) allClasses);
        setRequestHandlerBeanName(requestHandlerBeanName);
    }

    @SuppressWarnings("unchecked")
    public RequestHandlerFactory(Class<B> requestBodyDbQueryClasses, Class<R> responseBodyClass, String requestHandlerBeanName) {
        setRequestBodyClass(requestBodyDbQueryClasses);
        setDbEntityClass((Class<D>) requestBodyDbQueryClasses);
        setResponseBodyClass(responseBodyClass);
        setRequestHandlerBeanName(requestHandlerBeanName);
    }

    public RequestHandlerFactory(Class<B> requestBodyClass, Class<D> dbEntityClass, Class<R> responseBodyClass, String requestHandlerBeanName) {
        setRequestBodyClass(requestBodyClass);
        setDbEntityClass(dbEntityClass);
        setResponseBodyClass(responseBodyClass);
        setRequestHandlerBeanName(requestHandlerBeanName);
    }

    /* Custom Methods */

    public ResponseEntity<String> emptyResponse() {
        return ResponseEntity.ok().body("");
    }

    @SuppressWarnings("unchecked")
    public RequestHandler<B, D, R> spawnRequestHandler() {
        return getContext().getBean(getRequestHandlerBeanName(), RequestHandler.class);
    }

    /* Override handleRequest Methods */

    public ResponseEntity<String> handleRequest() {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> pathParams) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setPathParams(pathParams);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setPathParams(pathParams);
        handler.setQueryParams(queryParams);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams, Map<String, String> headerParams) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setPathParams(pathParams);
        handler.setQueryParams(queryParams);
        handler.setHeaderParams(headerParams);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(B requestBody) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, B requestBody) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setPathParams(pathParams);
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams, B requestBody) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setPathParams(pathParams);
        handler.setQueryParams(queryParams);
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams, Map<String, String> headerParams, B requestBody) {
        RequestHandler<B, D, R> handler = spawnRequestHandler();
        handler.setPathParams(pathParams);
        handler.setQueryParams(queryParams);
        handler.setHeaderParams(headerParams);
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    /* Setters and Getters */

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

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

    public void setRequestHandlerBeanName(String requestHandlerBeanName) {
        this.requestHandlerBeanName = requestHandlerBeanName;
    }

    public String getRequestHandlerBeanName() {
        return requestHandlerBeanName;
    }
}