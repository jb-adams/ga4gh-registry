package org.ga4gh.registry.util.requesthandler;

import org.ga4gh.registry.model.RegistryEntity;

public class SingleGenericRequestHandlerFactory<D extends RegistryEntity> extends RequestHandlerFactory<D, D, D> {

    public SingleGenericRequestHandlerFactory(Class <D> allClasses, String requestHandlerBeanName) {
        super(allClasses, requestHandlerBeanName);
    }
}
