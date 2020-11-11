package org.ga4gh.registry.util.requesthandler;

import java.util.Map;

import org.ga4gh.registry.model.RegistryModel;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;

public interface RequestHandlerFactoryI<B extends RegistryModel> extends ApplicationContextAware {
    public ResponseEntity<String> handleRequest();
    public ResponseEntity<String> handleRequest(Map<String, String> pathParams);
    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams);
    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams, Map<String, String> headerParams);
    public ResponseEntity<String> handleRequest(B requestBody);
    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, B requestBody);
    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams, B requestBody);
    public ResponseEntity<String> handleRequest(Map<String, String> pathParams, Map<String, String> queryParams, Map<String, String> headerParams, B requestBody);
}