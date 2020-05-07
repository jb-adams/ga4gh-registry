package org.ga4gh.registry.util.response;

import java.util.StringJoiner;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Queryable;

public class HibernateQueryBuilder {

    private Class<? extends Queryable> responseClass;
    private StringBuffer joinBuffer;
    private StringJoiner filterBuffer;

    public HibernateQueryBuilder() {
        responseClass = Implementation.class;
        joinBuffer = new StringBuffer();
        filterBuffer = new StringJoiner(" AND ");
    }

    public void join(String property) {
        // joinBuffer.append("JOIN FETCH a." + property + " ");
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
        System.out.println("Building Query String");
        System.out.println(queryString);
        return queryString;
    }

    public String getStartString() {
        return "select distinct a from " + this.responseClass.getName() + " a ";
    }

    public void setResponseClass(Class<? extends Queryable> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<? extends Queryable> getResponseClass() {
        return responseClass;
    }
}