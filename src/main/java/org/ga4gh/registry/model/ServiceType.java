package org.ga4gh.registry.model;

import org.ga4gh.registry.exception.BadRequestException;

/** ServiceType model, represents the 'type' of API, that is, the API specification
 * that a web service adopts. Service Types are not database entities directly,
 * but can be derived from the standard and version a service is related to  
 * 
 * @author GA4GH Technical Team
 */
public class ServiceType implements RegistryModel {

    private String group;
    private String artifact;
    private String version;

    /* Constructors */

    /** Instantiate a ServiceType
     */
    public ServiceType() {}

    /** Instantiate a ServiceType from 'Type' string. Type strings have the 
     * format of "{GROUP}:{ARTIFACT}:{VERSION}"
     * 
     * @param type comma-delimited type string
     * @throws BadRequestException
     */
    public ServiceType(String type) throws BadRequestException {
        String[] components = type.split(":");
        if (components.length != 3)  {
            throw new BadRequestException(
                "Could not instantiate ServiceType from 'type' string");
        }
        setGroup(components[0]);
        setArtifact(components[1]);
        setVersion(components[2]);

        if (!getGroup().equals("org.ga4gh")) {
            throw new BadRequestException(
                "Invalid type 'group' parameter");
        }
    }

    /** Instantiate a ServiceType
     * 
     * @param group standards group owning the API spec (e.g. org.ga4gh)
     * @param artifact unique artifact for this service type (e.g. htsget)
     * @param version version of the API specification the service adopts
     */
    public ServiceType(String group, String artifact, String version) {
        setGroup(group);
        setArtifact(artifact);
        setVersion(version);
    }

    /** Export the ServiceType as a comma-delimited signature/type string
     * 
     * @return ServiceType properties (group, artifact, version) exported as type string
     */
    public String signature() {
        return getGroup() + ":" + getArtifact() + ":" + getVersion();
    }

    /* Setters and Getters */

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "ServiceType ["
               + "group=" + group + ", "
               + "artifact=" + artifact + ", "
               + "version=" + version + "]";
    }

    @Override
    public boolean equals(Object o) {
        boolean equals = false;
        if (toString().equals(o.toString())) {
            equals = true;
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}