package org.ga4gh.registry.util.requesthandler.index;

import java.util.List;

import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class IndexRequestHandler<B extends RegistryModel, D extends RegistryEntity, R extends RegistryModel> extends RequestHandler<B, D, R> {

    HibernateQuerier<D> querier;

    @Autowired
    HibernateQueryBuilder queryBuilder;

    @Autowired
    public IndexRequestHandler(Class<B> allClasses, RegistrySerializerModule serializerModule, HibernateQuerier<D> querier) {
        super(allClasses, serializerModule);
        setQuerier(querier);
    }

    public List<D> getResultsFromDb() {
        HibernateQuerier<D> q = getQuerier();
        HibernateQueryBuilder qb = getQueryBuilder();
        qb.setResponseClass(q.getTypeClass());
        q.setQueryString(qb.build());
        return q.getResults();
    }

    public ResponseEntity<String> createResponseEntity() {
        ResponseEntity<String> responseEntity = null;
        List<D> dbResults = getResultsFromDb();
        List<R> response = transformDbResults(dbResults);
        responseEntity = ResponseEntity.ok().body(serializeObject(response));
        return responseEntity;
    }

    public void setQuerier(HibernateQuerier<D> querier) {
        this.querier = querier;
    }

    public HibernateQuerier<D> getQuerier() {
        return querier;
    }

    public void setQueryBuilder(HibernateQueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public HibernateQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
}
