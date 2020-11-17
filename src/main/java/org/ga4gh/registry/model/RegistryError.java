package org.ga4gh.registry.model;

import java.util.Date;

/** Represents an error to be passed back to client when any error is encountered
 * during an API controller method
 * 
 * @author GA4GH Technical Team
 */
public class RegistryError {

    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    /* Constructors */

    /** Instantiate a RegistryError
     */
    public RegistryError() {}

    /** Instantiate a RegistryError
     * 
     * @param status HTTP status code of the error
     * @param message debug message indicating reason for encountered error
     */
    public RegistryError(int status, String message) {
        setStatus(status);
        setMessage(message);
    }

    /* Setters and Getters */

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "RegistryError [status=" + getStatus() 
            + ", message=" + getMessage() + "]";
    }
}