package org.ga4gh.registry.util.hibernate;

import java.util.StringJoiner;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.RegistryModel;

/** Builds a complex hibernate query language (HQL) database query. Allows for
 * data joins, filters, etc.
 * 
 * @author GA4GH Technical Team
 */
public class HibernateQueryBuilder {

    private Class<? extends RegistryModel> responseClass;
    private StringBuffer joinBuffer;
    private StringJoiner filterBuffer;

    /* Constructors */

    /** Instantiate a HibernateQueryBuilder
     */
    public HibernateQueryBuilder() {
        responseClass = Implementation.class;
        joinBuffer = new StringBuffer();
        filterBuffer = new StringJoiner(" AND ");
    }

    /** Add a data join to the hibernate query, joining data across entity tables
     * 
     * @param property The Java Entity property name to be joined
     */
    public void join(String property) {
        joinBuffer.append("LEFT JOIN FETCH a." + property + " ");
    }

    /** Add a filtering clause to the hibernate query, requiring a particular
     * field to equal a certain value
     * 
     * @param property The Java Entity property name to be filtered
     * @param value the corresponding value the property must have to be returned
     */
    public void filter(String property, String value) {
        filterBuffer.add("a." + property + "='" + value + "'");
    }

    /** Builds the hibernate query based on contained properties (joins and filters)
     * 
     * @return the hibernate query string
     */
    public String build() {
        String queryString = getStartString() + joinBuffer.toString();
        if (filterBuffer.length() > 0) {
            queryString += "WHERE ";
            queryString += filterBuffer.toString();
        }
        queryString += getOrderString();
        return queryString;
    }

    /** 
     * @return The start of a hibernate query as a string
     */
    public String getStartString() {
        return "select distinct a from " + this.responseClass.getName() + " a ";
    }

    /**
     * @return A string constant to order items by id
     */
    public String getOrderString() {
        return " ORDER BY a.id ";
    }

    /* Setters and Getters */
    
    public void setResponseClass(Class<? extends RegistryModel> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<? extends RegistryModel> getResponseClass() {
        return responseClass;
    }

    public StringJoiner getFilterBuffer() {
        return filterBuffer;
    }
}