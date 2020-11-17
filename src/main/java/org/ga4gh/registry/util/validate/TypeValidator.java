package org.ga4gh.registry.util.validate;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.model.ServiceType;

/** Validates the 'type' parameter, which specifies a ServiceType
 * 
 * @author GA4GH Technical Team
 */
public final class TypeValidator {

    /** Validates the 'type' parameter, which specifies a ServiceType in
     * comma-delimited syntax (GROUP:ARTIFACT:VERSION). Throws an exception
     * if the type parameter value does not adhere to this schema
     * 
     * @param type comma-delimited string indicating ServiceType
     * @throws BadRequestException
     */
    public static void validate(String type) throws BadRequestException {

        if (type != null) {
            try {
                new ServiceType(type);
            } catch (InstantiationError e) {
                throw new BadRequestException(e);
            }
        }
    }
}