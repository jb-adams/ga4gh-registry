package org.ga4gh.registry.model;

/** Model representing the resolution of a URL from a passed compact identifier
 * (e.g. CURIE). Generally, the resolved URL will point to a specific object stored
 * behind one of the services in the registry. URIResolutions are transient,
 * they are not stored in the database, though registered service entities
 * are used to resolve request CURIEs
 * 
 * @author GA4GH Technical Team
 */
public class URIResolution implements RegistryModel {

    private String serviceId;
    private String serviceName;
    private ServiceType serviceType;
    private String resolvedURL;

    /* Constructors */

    /** Instantiate a URIResolution
     */
    public URIResolution() {

    }

    /** Instantiate a URIResolution
     * 
     * @param serviceId The ID of the service that the resolved URL points to
     * @param serviceName The name of the service that the resolved URL points to
     * @param serviceType The ServiceType (group, artifact, version) of the service
     * @param resolvedURL The full URL resolved based on a passed CURIE/identifier 
     */
    public URIResolution(String serviceId, String serviceName, ServiceType serviceType, String resolvedURL) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.resolvedURL = resolvedURL;
    }

    /* Setters and Getters */

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

    @Override
    public String toString() {
        return "URIResolution: ["
               + "serviceId=" + serviceId + ", "
               + "serviceName=" + serviceName + ", "
               + "resolvedURL=" + resolvedURL + "]";
    }
}
