package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.ga4gh.registry.util.requesthandler.SingleGenericRequestHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** REST API Controller for Service model
 * @author GA4GH Technical Team
 */
@EnableWebMvc
@RestController
@RequestMapping("/services")
public class Services {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_SERVICE_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Service> indexService;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_SERVICE_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Service> showService;

    @Autowired
    @Qualifier(AppConfigConstants.POST_SERVICE_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Service> postService;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_SERVICE_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Service> putService;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_SERVICE_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Service> deleteService;

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER_FACTORY)
    RequestHandlerFactory<Service, Service, ServiceType> indexServiceType;

    /** Get all registered services
     * 
     * @param queryVariables parameters in query string, allows filtering of returned services
     * @return response entity containing services list
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServices(@RequestParam Map<String, String> queryVariables) {
        return indexService.handleRequest(null, queryVariables);
    }

    /** Get a registered service by its ID
     * 
     * @param pathVariables parameters on URL path, contains the requested ID
     * @return response entity containing requested service
     */
    @GetMapping(path = "/{serviceId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceById(@PathVariable Map<String, String> pathVariables) {
        return showService.handleRequest(pathVariables);
    }

    /** Register a new service
     * 
     * @param service request body containing new service properties
     * @return response entity containing the created service
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createService(@RequestBody Service service) {
        return postService.handleRequest(service);
    }

    /** Update an existing service
     * 
     * @param pathParams parameters on URL path, contains the ID of service to update
     * @param service request body contains service properties to update with
     * @return response entity containing the updated service
     */
    @PutMapping(path = "/{serviceId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateServiceById(@PathVariable Map<String, String> pathParams, @RequestBody Service service) {
        return putService.handleRequest(pathParams, service);
    }

    /** Delete an existing service
     * 
     * @param pathParams parameters on URL path, contains the ID of service to delete
     * @return response entity with empty body, indicating successful deletion
     */
    @DeleteMapping(path = "/{serviceId:.+}")
    public ResponseEntity<String> deleteServiceById(@PathVariable Map<String, String> pathParams) {
        return deleteService.handleRequest(pathParams);
    }

    /** Get list of all unique service types in the service registry
     * 
     * @return response entity containing unique service types in the registry
     */
    @GetMapping(path = "/types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceTypes() {
        return indexServiceType.handleRequest();
    }
}
