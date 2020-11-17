package org.ga4gh.registry.util.auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ga4gh.registry.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Value;

/** Temporary auth class that validates whether the authorization header token
 * matches the expected value. This is a temporary solution, and should be
 * replaced by allowing auth with real admin account tokens.
 * 
 * @author GA4GH Technical Team
 */
public class PlaceholderAuth {

    @Value("${registry.auth.secret}")
    private String secret;

    /** Instantiate a PlaceholderAuth
     */
    public PlaceholderAuth() {

    }

    /** Authorizes a request by checking for a match of the request's auth 
     * header token to the expected secret value. If a match isn't found, a 
     * forbidden exception is thrown
     * 
     * @param authHeader HTTP request authorization header value 
     * @throws ForbiddenException when the request auth token doesn't match expected
     */
    public void authorize(String authHeader) throws ForbiddenException {
        // absent auth header throws forbidden exception
        if (authHeader == null) {
            throw new ForbiddenException("no authorization token provided");
        }

        // malformed auth header (i.e. not matching "bearer {TOKEN}" pattern)
        // throws forbidden exception
        Pattern pattern = Pattern.compile("^bearer (.+)$");
        Matcher matcher = pattern.matcher(authHeader);
        if (!matcher.find()) {
            throw new ForbiddenException("authorization header malformed");
        }

        // token value mismatch throws forbidden exception
        String token = matcher.group(1);
        if (!getSecret().equals(token)) {
            throw new ForbiddenException("invalid token");
        }
    }

    /* Setters and Getters */

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}