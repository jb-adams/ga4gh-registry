package org.ga4gh.registry.model;

import java.text.ParseException;

/** Curie model, represents a Compact Identifier (CURIE). Curies can be resolved
 * into data objects behind registered services. Curies are transient: they are
 * not stored in the database
 * 
 * @author GA4GH Technical Team
 */
public class Curie {

    private String prefix;
    private String id;

    /** Instantiates a Curie from a string in the correct format
     * 
     * @param curie CURIE string
     * @return Curie object based on information contained in passed CURIE string
     * @throws ParseException The passed string was not in valid CURIE format {PREFIX}:{ID}
     */
    public static Curie fromString(String curie) throws ParseException {
        String[] curieSplit = curie.split(":");
        if (curieSplit.length != 2) {
            throw new ParseException(curie + " is not in valid CURIE format", 0);
        }
        return new Curie(curieSplit[0], curieSplit[1]);
    }

    /* Constructors */

    /** Instantiates a Curie
     * 
     * @param prefix CURIE prefix (i.e. before the colon)
     * @param id CURIE's ID (i.e. after the colon)
     */
    private Curie(String prefix, String id) {
        setPrefix(prefix);
        setId(id);
    }

    /* Setters and Getters */

    private void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
