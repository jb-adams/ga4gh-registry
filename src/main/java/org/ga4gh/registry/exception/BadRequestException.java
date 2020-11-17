package org.ga4gh.registry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Spring Boot exception response handler for 400 Bad Request
 * @author GA4GH Technical Team
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Instantiates a BadRequestException
     */
    public BadRequestException() {
        super();
    }

    /** Instantiates a BadRequestException
     * 
     * @param message debug message indicating reason for Bad Request
     */
    public BadRequestException(String message) {
        super(message);
    }

    /** Instantiates a BadRequestException
     * 
     * @param cause exception containing reason for Bad Request
     */
    public BadRequestException(Throwable cause) {
        super(cause);
    }
}