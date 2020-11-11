package org.ga4gh.registry.util.requesthandler;

import org.ga4gh.registry.model.RegistryModel;

public class SingleGenericRequestHandlerFactory<B extends RegistryModel> extends RequestHandlerFactory<B, B, B> {

    public SingleGenericRequestHandlerFactory(Class <B> allClasses, String requestHandlerBeanName) {
        super(allClasses, requestHandlerBeanName);
    }
    
}
