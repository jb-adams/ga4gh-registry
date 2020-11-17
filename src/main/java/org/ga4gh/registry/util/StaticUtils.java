package org.ga4gh.registry.util;

/** static methods for use throughout the program
 * 
 * @author GA4GH Technical Team
 */
public class StaticUtils {

    /** Adds a trailing slash at the end of a string/URL if it isn't already
     * present
     * 
     * @param url the URL to add a trailing slash to
     * @return the URL with a trailing slash appended
     */
    public static String addTrailingSlash(String url) {
        String newURL = url;
        if (!newURL.endsWith("/")) {
            newURL += "/";
        } else {
        }
        return newURL;
    }
}
