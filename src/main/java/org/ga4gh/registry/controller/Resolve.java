package org.ga4gh.registry.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.URIResolution;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;

/**
 * REST API Controller for resolving URIs/URLs
 * @author GA4GH Technical Team
 */
@EnableWebMvc
@RestController
@RequestMapping("/resolve-uri")
public class Resolve {

    @Autowired
    @Qualifier(AppConfigConstants.RESOLVE_URI_HANDLER_FACTORY)
    RequestHandlerFactory<Service, Service, URIResolution> resolveURIHandlerFactory;

    /** Resolve a passed compact identifier (CURIE) to a URL referencing an
     * object behind a registered web service
     * 
     * @param pathParams parameters on URL path, contains CURIE
     * @return response entity with URL resolved to an object
     */
    @GetMapping(path = "/{uri:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> resolveURI(@PathVariable Map<String, String> pathParams) {
        return resolveURIHandlerFactory.handleRequest(pathParams);
    }
}
