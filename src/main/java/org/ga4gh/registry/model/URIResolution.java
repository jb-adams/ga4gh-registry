package org.ga4gh.registry.model;

public class URIResolution implements RegistryModel {

    private String serviceName;

    /* constructors */

    public URIResolution() {

    }

    public URIResolution(String serviceName) {
        this.serviceName = serviceName;
    }

    /* setters and getters */

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
