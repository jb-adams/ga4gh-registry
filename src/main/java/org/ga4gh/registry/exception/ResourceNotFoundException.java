package org.ga4gh.registry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Spring Boot exception response handler for 404 Not Found
 * @author GA4GH Technical Team
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Instantiates a ResourceNotFoundException
     */
    public ResourceNotFoundException() {
        super();
    }

    /** Instantiates a ResourceNotFoundException
     * 
     * @param message debug message indicating reason for Not Found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /** Instantiates a ResourceNotFoundException
     * 
     * @param cause exception containing reason for Not Found
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}