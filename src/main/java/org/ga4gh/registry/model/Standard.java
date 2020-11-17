package org.ga4gh.registry.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.Hibernate;

/** Standard model, represents a standard/specification. A single specification
 * can have multiple versions (see StandardVersion). Standards are database 
 * entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name="standard")
public class Standard implements RegistryEntity {

    public static final String tableName = "standard";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @Column(name = "artifact")
    private String artifact;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_category_id")
    private StandardCategory standardCategory;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "release_status_id")
    private ReleaseStatus releaseStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "work_stream_id")
    private WorkStream workStream;

    @OneToMany(mappedBy = "standard",
               fetch = FetchType.LAZY,
               cascade = {})
    private List<StandardVersion> standardVersions;

    @ManyToMany
    @JoinTable(
        name = "implementation_standard",
        joinColumns = {@JoinColumn(name = "standard_id")},
        inverseJoinColumns = {@JoinColumn(name = "implementation_id")}
    )
    private List<Implementation> implementations;

    /* Constructors */

    /** Instantiate a Standard
     */
    public Standard() {

    }

    /** Instantiate a Standard
     * 
     * @param name standard name
     * @param summary short, one line summary/tagline of the standard
     * @param description long description of the standard
     * @param documentationUrl Standard's homepage URL (e.g. Github repo)
     */
    public Standard(String name, String summary, String description, 
        String documentationUrl) {
            
        this.name = name;
        this.summary = summary;
        this.description = description;
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
        Hibernate.initialize(getStandardVersions());
        Hibernate.initialize(getImplementations());
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

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setStandardCategory(StandardCategory standardCategory) {
        this.standardCategory = standardCategory;
    }

    public StandardCategory getStandardCategory() {
        return standardCategory;
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setWorkStream(WorkStream workStream) {
        this.workStream = workStream;
    }

    public WorkStream getWorkStream() {
        return workStream;
    }

    public void setStandardVersions(List<StandardVersion> standardVersions) {
        this.standardVersions = standardVersions;
    }

    public List<StandardVersion> getStandardVersions() {
        return standardVersions;
    }

    public void setImplementations(List<Implementation> implementations) {
        this.implementations = implementations;
    }

    public List<Implementation> getImplementations() {
        return implementations;
    }

    @Override
    public String toString() {
        return "Standard [id=" + id + ", name=" + name + 
               ", summary=" + summary +
               ", description=" + description +
               ", documentationUrl=" + documentationUrl +
               ", artifact=" + artifact + "]";
    }
}
