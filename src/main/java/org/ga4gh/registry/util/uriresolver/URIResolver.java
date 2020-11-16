package org.ga4gh.registry.util.uriresolver;

import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.util.StaticUtils;

public class URIResolver {

    public String drsv1(Service service, String id) {
        return StaticUtils.addTrailingSlash(service.getUrl()) + "ga4gh/drs/v1/objects/" + id;
    }
}
