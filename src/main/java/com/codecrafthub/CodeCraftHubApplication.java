package com.codecrafthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CodeCraftHub Learning Management System
 * Main Spring Boot Application
 * 
 * This is a simple REST API for tracking learning goals and courses.
 */
@SpringBootApplication
public class CodeCraftHubApplication {
    
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("CodeCraftHub API is starting...");
        System.out.println("============================================================");
        System.out.println("Data will be stored in: courses.json");
        System.out.println("API will be available at: http://localhost:8080");
        System.out.println("============================================================");
        System.out.println("\nPress CTRL+C to stop the server\n");
        
        SpringApplication.run(CodeCraftHubApplication.class, args);
    }
}
