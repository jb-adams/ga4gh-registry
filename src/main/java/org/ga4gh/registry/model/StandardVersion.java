package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.Hibernate;

/** StandardVersion model, represents a single versioned release of a GA4GH 
 * standard. A StandardVersion will have its own unique version number and 
 * landing page. StandardVersion object are database entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name = "standard_version")
public class StandardVersion implements RegistryEntity {

    public static final String tableName = "standard_version";
    
    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "version_number")
    private String versionNumber;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_id")
    private Standard standard;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "release_status_id")
    private ReleaseStatus releaseStatus;

    @OneToMany(mappedBy="standardVersion",
               cascade=CascadeType.ALL)
    private List<Service> services;

    /* Constructors */

    /** Instantiate a StandardVersion
     */
    public StandardVersion() {

    }

    /** Instantiate a StandardVersion
     * 
     * @param versionNumber Official version number release of the standard
     * @param documentationUrl The versioned release's landing / documentation page
     */
    public StandardVersion(String versionNumber, String documentationUrl) {
        this.versionNumber = versionNumber;
        this.documentationUrl = documentationUrl;
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
        Hibernate.initialize(getServices());        
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    /* Setters and Getters */

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "StandardVersion [id=" + id 
               + ", versionNumber=" + versionNumber
               + ", documentationUrl=" + documentationUrl + "]";
    }
}
