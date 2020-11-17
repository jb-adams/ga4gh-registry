package org.ga4gh.registry.model;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.Hibernate;

/** Implementation model, represents a codebase/resource implementing one or 
 * more GA4GH standards. Implementations are database entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name = "implementation")
public class Implementation implements RegistryEntity {

    private static final String tableName = "implementation";

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

    @Column(name = "version")
    private String version;

    @ManyToOne(fetch = FetchType.EAGER,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany
    @JoinTable(
        name = "implementation_standard",
        joinColumns = {@JoinColumn(name = "implementation_id")},
        inverseJoinColumns = {@JoinColumn(name = "standard_id")}
    )
    private List<Standard> standards;

    /* Constructors */

    /** Instantiates an Implementation
     */
    public Implementation() {

    }

    /** Instantiates an Implementation
     * 
     * @param name implementation name
     * @param description implementation description
     * @param contactUrl contact URL (e.g. mailto:user@address.com) for this implementation
     * @param documentationUrl web page containing this implementation's documentation
     * @param createdAt timestamp indicating when implementation was first registered
     * @param updatedAt timestamp indicating when implementation properties were last updated in the registry
     * @param version implementation's version number
     */
    public Implementation(String name, String description, String contactUrl,
        String documentationUrl, Date createdAt, Date updatedAt, String version) {
        
        this.name = name;
        this.description = description;
        this.contactUrl = contactUrl;
        this.documentationUrl = documentationUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
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
        Hibernate.initialize(getStandards());
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

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    @Override
    public String toString() {
        return "Implementation ["
               + "id=" + id + ", "
               + "name=" + name + ", "
               + "description=" + description + ", "
               + "contactUrl=" + contactUrl + ", "
               + "documentationUrl=" + documentationUrl + ", "
               + "version=" + version + "]";
    }
}
