package org.ga4gh.registry.model;

public class URIResolution implements RegistryModel {

    private String serviceId;
    private String serviceName;
    private ServiceType serviceType;
    private String resolvedURL;

    /* constructors */

    public URIResolution() {

    }

    public URIResolution(String serviceId, String serviceName, ServiceType serviceType, String resolvedURL) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.resolvedURL = resolvedURL;
    }

    /* setters and getters */

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setResolvedURL(String resolvedURL) {
        this.resolvedURL = resolvedURL;
    }

    public String getResolvedURL() {
        return resolvedURL;
    }

    public String toString() {
        return "URIResolution: ["
               + "serviceId=" + serviceId + ", "
               + "serviceName=" + serviceName + ", "
               + "resolvedURL=" + resolvedURL + "]";
    }
}
