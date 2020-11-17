package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.requesthandler.SingleGenericRequestHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;

/** REST API Controller for Standard model
 * @author GA4GH Technical Team
 */
@EnableWebMvc
@RestController
@RequestMapping("/standards")
public class Standards {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_STANDARD_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Standard> indexStandard;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_STANDARD_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Standard> showStandard;

    @Autowired
    @Qualifier(AppConfigConstants.POST_STANDARD_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Standard> postStandard;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_STANDARD_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Standard> putStandard;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_STANDARD_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Standard> deleteStandard;

    /** Get all standards
     * 
     * @return response entity containing list of standards
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandards() {
        return indexStandard.handleRequest();
    }

    /** Get a registered standard by its ID
     * 
     * @param pathParams parameters on URL path, contains the requested ID
     * @return response entity containing the requested standard
     */
    @GetMapping(path = "/{standardId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandardById(@PathVariable Map<String, String> pathParams) {
        return showStandard.handleRequest(pathParams);
    }

    /** Register a new standard
     * 
     * @param standard request body containing new standard properties
     * @return response entity containing the created standard
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createStandard(@RequestBody Standard standard) {
        return postStandard.handleRequest(standard);
    }

    /** Update an existing standard
     * 
     * @param pathParams parameters on URL path, contains the ID of standard to update
     * @param standard request body contains standard properties to update with
     * @return response entity containing the updated service
     */
    @PutMapping(path = "/{standardId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateStandardById(@PathVariable Map<String, String> pathParams, @RequestBody Standard standard) {
        return putStandard.handleRequest(pathParams, standard);
    }

    /** Delete an existing standard
     * 
     * @param pathParams parameters on URL path, contains the ID of standard to delete
     * @return response entity with empty body, indicating successful deletion
     */
    @DeleteMapping(path = "/{standardId:.+}")
    public ResponseEntity<String> deleteStandardById(@PathVariable Map<String, String> pathParams) {
        return deleteStandard.handleRequest(pathParams);
    }
}
