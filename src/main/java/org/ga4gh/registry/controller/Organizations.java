package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.requesthandler.SingleGenericRequestHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.http.ResponseEntity;

/** REST API Controller for Organization model
 * @author GA4GH Technical Team
 */
@EnableWebMvc
@RestController
@RequestMapping("/organizations")
public class Organizations {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_ORGANIZATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Organization> indexOrganization;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_ORGANIZATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Organization> showOrganization;

    @Autowired
    @Qualifier(AppConfigConstants.POST_ORGANIZATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Organization> postOrganization;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_ORGANIZATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Organization> putOrganization;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_ORGANIZATION_HANDLER_FACTORY)
    SingleGenericRequestHandlerFactory<Organization> deleteOrganization;

    /** Get all registered organizations
     * 
     * @return response entity containing organizations list
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizations() {
        return indexOrganization.handleRequest();
    }

    /** Get a registered organization by its ID
     * 
     * @param pathParams parameters on URL path, contains the requested ID
     * @return response entity containing requested organization
     */
    @GetMapping(path = "/{organizationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizationById(@PathVariable Map<String, String> pathParams) {
        return showOrganization.handleRequest(pathParams);
    }

    /** Register a new organization
     * 
     * @param organization request body containing new organization properties
     * @return response entity containing the created organization
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization) {
        return postOrganization.handleRequest(organization);
    }

    /** Update an existing organization
     * 
     * @param pathParams parameters on URL path, contains the ID of organization to update
     * @param organization request body contains organization properties to update with
     * @return response entity containing the updated organization
     */
    @PutMapping(path = "/{organizationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateOrganizationById(@PathVariable Map<String, String> pathParams, @RequestBody Organization organization) {
        return putOrganization.handleRequest(pathParams, organization);        
    }

    /** Delete an existing organization
     * 
     * @param pathParams parameters on URL path, contains the ID of organization to delete
     * @return response entity with empty body, indicating successful deletion
     */
    @DeleteMapping(path = "/{organizationId:.+}")
    public ResponseEntity<String> deleteOrganizationById(@PathVariable Map<String, String> pathParams) {
        return deleteOrganization.handleRequest(pathParams);
    }   
}