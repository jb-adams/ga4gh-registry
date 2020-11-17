package org.ga4gh.registry.model;

/** Interface representing any model that is a database entity
 * 
 * @author GA4GH Technical Team
 */
public interface RegistryEntity extends RegistryModel {

    /** Set the entity's id
     * 
     * @param id entity id
     */
    public void setId(String id);

    /** Retrieve the entity's id
     * 
     * @return entity id
     */
    public String getId();

    /** Load all associated attributes through relational mappings to other tables.
     * This method MUST load all attributes related by a Lazy fetch type when called
     * (before the transaction is closed).
     */
    public void lazyLoad();

    /** Retrieve the table name for this entity
     * 
     * @return table name
     */
    public String getTableName();
}
