package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** WorkStream model, represents a single GA4GH technical or foundational work stream.
 * Standards belong to one primary work stream. WorkStream objects are database
 * entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name="work_stream")
public class WorkStream implements RegistryEntity {

    public static final String tableName = "work_stream";

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "abbreviation")
    private String abbreviation;

    @OneToMany(mappedBy = "workStream",
               cascade=CascadeType.ALL)
    private List<Standard> standards;

    /* Constructors */

    /** Instantiate a WorkStream
     */
    public WorkStream() {

    }

    /** Instantiate a WorkStream
     * 
     * @param name work stream name
     * @param abbreviation work stream short name, abbreviation, or acronym
     */
    public WorkStream(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
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

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    @Override
    public String toString() {
        return "WorkStream [id=" + id
            + ", name=" + name 
            + ", abbreviation=" + abbreviation + "]";
    }
}