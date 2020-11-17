package org.ga4gh.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Spring Boot Application class containing main method. Runs the API
 * 
 * @author GA4GH Technical Team
 */
@SpringBootApplication
public class App {

    /** Main method, runs the API
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
