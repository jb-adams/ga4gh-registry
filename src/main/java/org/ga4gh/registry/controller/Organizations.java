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

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizations() {
        return indexOrganization.handleRequest();
    }

    @GetMapping(path = "/{organizationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizationById(@PathVariable Map<String, String> pathParams) {
        return showOrganization.handleRequest(pathParams);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization) {
        return postOrganization.handleRequest(organization);
    }

    @PutMapping(path = "/{organizationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateOrganizationById(@PathVariable Map<String, String> pathParams, @RequestBody Organization organization) {
        return putOrganization.handleRequest(pathParams, organization);        
    }

    @DeleteMapping(path = "/{organizationId:.+}")
    public ResponseEntity<String> deleteOrganizationById(@PathVariable Map<String, String> pathParams) {
        return deleteOrganization.handleRequest(pathParams);
    }   
}