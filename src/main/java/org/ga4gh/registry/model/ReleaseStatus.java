package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.Hibernate;

/** ReleaseStatus model, represents the status of a GA4GH standard 
 * (e.g. Approved, InProgress, Proposed, Archived, etc.). ReleaseStatus objects
 * are database entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name = "release_status")
public class ReleaseStatus implements RegistryEntity {

    public static final String tableName = "release_status";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "releaseStatus",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<Standard> standards;

    @OneToMany(mappedBy = "releaseStatus",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<StandardVersion> standardVersions;

    /* Constructors */

    /** Instantiate a ReleaseStatus
     */
    public ReleaseStatus() {

    }

    /** Instantiate a ReleaseStatus
     * 
     * @param status name/description of the standard's status
     */
    public ReleaseStatus(String status) {
        this.status = status;
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
        Hibernate.initialize(getStandardVersions());
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    /* Setters and Getters */

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandardVersions(List<StandardVersion> standardVersions) {
        this.standardVersions = standardVersions;
    }

    public List<StandardVersion> getStandardVersions() {
        return standardVersions;
    }

    @Override
    public String toString() {
        return "ReleaseStatus [id=" + id + ", status=" + status + "]";
    }
}
