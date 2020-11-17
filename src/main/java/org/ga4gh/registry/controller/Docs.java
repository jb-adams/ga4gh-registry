package org.ga4gh.registry.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/** REST API Controller for getting documentation from static file directory
 * @author GA4GH Technical Team
 */
@EnableWebMvc
@RestController
@RequestMapping("/docs")
public class Docs {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /** Serves the contents of a requested file located in static file directory 
     * 
     * @param request The HTTP request object
     * @return response entity containing contents of static file
     * @throws IOException
     */
    @GetMapping(value = "/**")
    public ResponseEntity<String> getDocByFileName(HttpServletRequest request) throws IOException {

        // map the requested URL to a file in the static directory, and attempt
        // to load as a resource
        String urlPathPrefix = getContextPath() + "/docs";
        String resourcePath = request.getRequestURI().replaceFirst(urlPathPrefix, "/static");
        ClassPathResource resource = new ClassPathResource(resourcePath);
        ResponseEntity<String> responseEntity = ResponseEntity.notFound().build();

        // if resource exists, read and send back to client
        if (resource.exists()) {
            String body = new String(resource.getInputStream().readAllBytes());
            responseEntity = ResponseEntity.ok().body(body);
        }
        return responseEntity;
    }

    private String getContextPath() {
        return contextPath;
    }
}
