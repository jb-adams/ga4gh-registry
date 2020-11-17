package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.Hibernate;

/** Organization model, represents an organization implementing Implementations
 * and/or Services within the registry. Organizations are database entities
 * 
 * @author GA4GH Technical Team
 */
@Entity
@Table(name = "organization")
public class Organization implements RegistryEntity {

    private static final String tableName = "organization";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "url")
    @NotNull
    private String url;

    @OneToMany(mappedBy = "organization",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    @OrderBy("id")
    private List<Implementation> implementations;

    @OneToMany(mappedBy = "organization",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    @OrderBy("id")
    private List<Service> services;

    /* Constructors */

    /** Instantiates an Organization
     */
    public Organization() {}

    /** Instantiates an Organization
     * 
     * @param name full name
     * @param shortName short name or abbreviation
     * @param url http(s) URL to org's home page
     */
    public Organization(String name, String shortName, String url) {
        this.name = name;
        this.shortName = shortName;
        this.url = url;
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
        Hibernate.initialize(getImplementations());
        Hibernate.initialize(getServices());
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

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setImplementations(List<Implementation> implementations) {
        this.implementations = implementations;
    }

    public List<Implementation> getImplementations() {
        return implementations;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "Organization [id=" + id + ", name=" + name +
               ", shortName=" + shortName + ", url=" + url + "]";
    }
}