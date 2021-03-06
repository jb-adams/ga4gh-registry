package org.ga4gh.registry.util.hibernate;

import java.util.StringJoiner;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.RegistryModel;

public class HibernateQueryBuilder {

    private Class<? extends RegistryModel> responseClass;
    private StringBuffer joinBuffer;
    private StringJoiner filterBuffer;

    public HibernateQueryBuilder() {
        responseClass = Implementation.class;
        joinBuffer = new StringBuffer();
        filterBuffer = new StringJoiner(" AND ");
    }

    public void join(String property) {
        joinBuffer.append("LEFT JOIN FETCH a." + property + " ");
    }

    public void filter(String property, String value) {
        filterBuffer.add("a." + property + "='" + value + "'");
    }

    public String build() {
        String queryString = getStartString() + joinBuffer.toString();
        if (filterBuffer.length() > 0) {
            queryString += "WHERE ";
            queryString += filterBuffer.toString();
        }
        queryString += getOrderString();
        return queryString;
    }

    public String getStartString() {
        return "select distinct a from " + this.responseClass.getName() + " a ";
    }

    public String getOrderString() {
        return " ORDER BY a.id ";
    }

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