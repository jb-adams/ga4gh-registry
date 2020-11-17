package org.ga4gh.registry.model;

/** Generic interface representing any class/model that may be passed as a 
 * request body, or returned in a response body. Classes implementing 
 * RegistryModel do not necessarily need to be database entities, but can be
 * (and often are)
 * 
 * @author GA4GH Technical Team
 */
public interface RegistryModel {}