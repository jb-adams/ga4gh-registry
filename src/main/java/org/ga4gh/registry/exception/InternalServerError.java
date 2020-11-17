package org.ga4gh.registry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Spring Boot exception response handler for 500 Internal Server Error
 * @author GA4GH Technical Team
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Instantiates an InternalServerError
     */
    public InternalServerError() {
        super();
    }

    /** Instantiates an InternalServerError
     * 
     * @param message debug message indicating reason for InternalServerError
     */
    public InternalServerError(String message) {
        super(message);
    }

    /** Instantiates an InternalServerError
     * 
     * @param cause exception containing reason for InternalServerError
     */
    public InternalServerError(Throwable cause) {
        super(cause);
    }
}