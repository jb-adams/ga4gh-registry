package org.ga4gh.registry.constant;

import java.util.Map;
import static java.util.Map.entry;

/** String constants of descriptions for HTTP status codes
 * @author GA4GH Technical Team
 */
public class HttpStatusName {

    /** HTTP OK description
     */
    public static final String OK = "OK";

    /** HTTP Bad Request description
     */
    public static final String BAD_REQUEST = "Bad Request";

    public static final String FORBIDDEN = "Forbidden";

    /** HTTP Not Found description
     */
    public static final String NOT_FOUND = "Not Found";

    /** HTTP unspecified server-side error description
     */
    public static final String SERVER_ERROR = "Server Error";

    /** HTTP internal server error description
     */
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    /** Maps status code numbers (as strings) to corresponding descriptions
     */
    public static final Map<String, String> HTTP_STATUS_NAMES = Map.ofEntries(
        entry(HttpStatusCode.OK, OK),
        entry(HttpStatusCode.BAD_REQUEST, BAD_REQUEST),
        entry(HttpStatusCode.FORBIDDEN, FORBIDDEN),
        entry(HttpStatusCode.NOT_FOUND, NOT_FOUND),
        entry(HttpStatusCode.SERVER_ERROR, SERVER_ERROR),
        entry(HttpStatusCode.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR)
    );

    /** Gets an HTTP status code description from its code
     * @param code HTTP status code number (as a string)
     * @return The description matching the passed status code
     */
    public static String get(String code) {
        return HTTP_STATUS_NAMES.get(code);
    }
}
