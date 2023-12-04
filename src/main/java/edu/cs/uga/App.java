package edu.cs.uga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * The main springboot application class.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class App
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
