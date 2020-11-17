package org.ga4gh.registry.util.uriresolver;

import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.util.StaticUtils;

/** Contains methods for resolving CURIEs to objects behind registered services
 * based on the GA4GH API type 
 * 
 * @author GA4GH Technical Team
 */
public class URIResolver {

    /** Resolves URLs to DRS objects behind DRS services based on the canonical
     * path to DRS objects 
     * 
     * @param service The DRS web service
     * @param id The DRS object id
     * @return A resolved URL string pointing to the DRS object
     */
    public String drsv1(Service service, String id) {
        return StaticUtils.addTrailingSlash(service.getUrl()) + "ga4gh/drs/v1/objects/" + id;
    }
}
