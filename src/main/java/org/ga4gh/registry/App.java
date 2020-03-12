/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.ga4gh.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

    private static ConfigurableApplicationContext context;
    
    public static void main(String[] args) {
        context = SpringApplication.run(App.class, args);
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
