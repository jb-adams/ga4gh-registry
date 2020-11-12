package org.ga4gh.registry.model;

public interface RegistryEntity extends RegistryModel {
    public void setId(String id);
    public String getId();
    public void lazyLoad();
    public String getTableName();
}
