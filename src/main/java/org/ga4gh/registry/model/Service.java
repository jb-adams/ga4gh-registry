package org.ga4gh.registry.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/** Service model, represents a running web service / API that implements a 
 * GA4GH API Specification. The service's 'type' will correspond to the API
 * specification it implements. Services are database entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name = "service")
public class Service implements RegistryEntity {

    private static final String tableName = "service";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "contact_url")
    private String contactUrl;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "environment")
    private String environment;

    @Column(name = "version")
    private String version;

    @Column(name = "url")
    private String url;

    @Column(name = "curie_prefix")
    private String curiePrefix;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_version_id")
    private StandardVersion standardVersion;

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Transient
    private ServiceType type;

    /* Constructors */

    /** Instantiates a Service
     */
    public Service() {

    }

    /** Instantiates a Service
     * 
     * @param name service name
     * @param description service description
     * @param contactUrl contact URL (e.g. mailto:user@address.com) for this service
     * @param documentationUrl web page containing this service's documentation (e.g. OpenAPI docs)
     * @param createdAt timestamp indicating when service was first registered
     * @param updatedAt timestamp indicating when service properties were last updated in the registry
     * @param environment the deployment environment of the service (e.g. prod, dev)
     * @param version service's version number
     * @param url service's base URL
     * @param curiePrefix the CURIE prefix associated with the service
     */
    public Service(String name, String description, String contactUrl,
        String documentationUrl, Date createdAt, Date updatedAt,
        String environment, String version, String url, String curiePrefix) {

        this.name = name;
        this.description = description;
        this.contactUrl = contactUrl;
        this.documentationUrl = documentationUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.environment = environment;
        this.version = version;
        this.url = url;
        this.curiePrefix = curiePrefix;
    }

    /* Override RegistryEntity */

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public String getTableName() {
        return tableName;
    }

    /* Setters and Getters */

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setCuriePrefix(String curiePrefix) {
        this.curiePrefix = curiePrefix;
    }

    public String getCuriePrefix() {
        return curiePrefix;
    }

    public void setStandardVersion(StandardVersion standardVersion) {
        this.standardVersion = standardVersion;
    }

    public StandardVersion getStandardVersion() {
        return standardVersion;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public ServiceType getType() {
        return type;
    }

    public ServiceType getServiceType() {
        ServiceType serviceType = new ServiceType();
        if (getStandardVersion() != null) {
            if (getStandardVersion().getStandard() != null) {
                String artifact = getStandardVersion().getStandard().getArtifact();
                String version = getStandardVersion().getVersionNumber();
                serviceType.setGroup("org.ga4gh");
                serviceType.setArtifact(artifact);
                serviceType.setVersion(version);
            }
        }
        return serviceType;
    }

    @Override
    public String toString() {
        return "Service ["
               + "id=" + id + ", "
               + "name=" + name + ", "
               + "description=" + description + ", "
               + "contactUrl=" + contactUrl + ", "
               + "documentationUrl=" + documentationUrl + ", "
               + "environment=" + environment + ", "
               + "version=" + version + ", "
               + "url=" + url + "]";
    }
}