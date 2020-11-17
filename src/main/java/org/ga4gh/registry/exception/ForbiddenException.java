package org.ga4gh.registry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Spring Boot exception response handler for 403 Forbidden
 * @author GA4GH Technical Team
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Instantiates a ForbiddenException
     */
    public ForbiddenException() {
        super();
    }

    /** Instantiates a ForbiddenException
     * 
     * @param message debug message indicating reason for Forbidden
     */
    public ForbiddenException(String message) {
        super(message);
    }

    /** Instantiates a ForbiddenException
     * 
     * @param cause exception containing reason for Forbidden
     */
    public ForbiddenException(Throwable cause) {
        super(cause);
    }
}