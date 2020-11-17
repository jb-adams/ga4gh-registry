package org.ga4gh.registry.exception;

import java.util.Date;
import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.util.serialize.serializers.DateSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

/** Intercepts Spring Boot's default error attributes with custom attributes
 * to be returned in an error response, especially when an error is thrown mid
 * controller method
 * 
 * @author GA4GH Technical Team
 */
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Autowired
    @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER)
    private DateSerializer dateSerializer;
    
    /** Gets custom error attributes on top of defaults
     * @param webRequest The web request
     * @param includeStackTrace Boolean indicating whether to include stack trace in error message
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("timestamp", getDateSerializer().getFormatter().format(new Date()));
        return errorAttributes;
    }

    /** Sets the date serializer so timestamps can be serialized according to
     * consistent format
     * 
     * @param dateSerializer The date serializer object
     */
    public void setDateSerializer(DateSerializer dateSerializer) {
        this.dateSerializer = dateSerializer;
    }

    /** Gets the date serializer object
     * 
     * @return The date serializer object
     */
    public DateSerializer getDateSerializer() {
        return dateSerializer;
    }
}