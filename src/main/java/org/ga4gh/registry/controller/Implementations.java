package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.SingleGenericRequestHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

/** REST API Controller for Implementation model
 * @author GA4GH Technical Team
 */
@EnableWebMvc
@RestController
@RequestMapping("/implementations")
public class Implementations {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Implementation> indexImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Implementation> showImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.POST_IMPLEMENTATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Implementation> postImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_IMPLEMENTATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Implementation> putImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Implementation> deleteImplementation;

    /** Get all registered implementations
     * 
     * @param queryParams query string parameters
     * @return response entity containing implementations list
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getImplementations(@RequestParam Map<String, String> queryParams) {
        return indexImplementation.handleRequest(null, queryParams);
    }

    /** Get a registered implementation by its ID
     * 
     * @param pathParams parameters on URL path, contains the requested ID
     * @return response entity containing requested implementation
     */
    @GetMapping(path = "/{implementationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getImplementationById(@PathVariable Map<String, String> pathParams) {
        return showImplementation.handleRequest(pathParams);
    }

    /** Register a new implementation
     * 
     * @param implementation request body contains new implementation properties
     * @return response entity containing the created implementation
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createImplementation(@RequestBody Implementation implementation) {
        return postImplementation.handleRequest(implementation);
    }

    /** Update an existing implementation
     * 
     * @param pathParams parameters on URL path, contains the ID of implementation to update
     * @param implementation request body contains implementation properties to update with
     * @return response entity containing the updated implementation
     */
    @PutMapping(path = "/{implementationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateImplementationById(@PathVariable Map<String, String> pathParams, @RequestBody Implementation implementation) {
        return putImplementation.handleRequest(pathParams, implementation);
    }

    /** Delete an existing implementation
     * 
     * @param pathParams parameters on URL path, contains ID of implementation to delete
     * @return response entity with empty body, indicating successful deletion
     */
    @DeleteMapping(path = "/{implementationId:.+}")
    public ResponseEntity<String> deleteImplementationById(@PathVariable Map<String, String> pathParams) {
        return deleteImplementation.handleRequest(pathParams);
    }
}
