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

/** StandardCategory model, represents a category of GA4GH standard (e.g. 
 * API, File Format, etc). Standard Categories are database entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name = "standard_category")
public class StandardCategory implements RegistryEntity {

    public static final String tableName = "standard_category";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "standardCategory",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<Standard> standards;

    /* Constructors */

    /** Instantiate a StandardCategory
     */
    public StandardCategory() {

    }

    /** Instantiate a StandardCategory
     * 
     * @param category name/description of the standard's category
     */
    public StandardCategory(String category) {
        this.category = category;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    @Override
    public String toString() {
        return "StandardCategory [id=" + id + ", category=" + category + "]";
    }
}
