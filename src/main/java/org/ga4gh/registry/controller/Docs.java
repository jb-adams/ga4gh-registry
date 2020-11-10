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

@EnableWebMvc
@RestController
@RequestMapping("/docs")
public class Docs {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping(value = "/**")
    public ResponseEntity<String> getDocByFileName(HttpServletRequest request) throws IOException {
        String urlPathPrefix = getContextPath() + "/docs";
        String resourcePath = request.getRequestURI().replaceFirst(urlPathPrefix, "/static");
        ClassPathResource resource = new ClassPathResource(resourcePath);
        ResponseEntity<String> responseEntity = ResponseEntity.notFound().build();
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
