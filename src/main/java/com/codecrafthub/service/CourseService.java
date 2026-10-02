package com.codecrafthub.service;

import com.codecrafthub.model.Course;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CourseService - Service Layer
 * Handles business logic and file operations for courses
 */
@Service
public class CourseService {
    
    // Configuration
    private static final String DATA_FILE = "courses.json";
    
    // Jackson ObjectMapper for JSON processing
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // ============================================================================
    // FILE OPERATIONS
    // ============================================================================
    
    /**
     * Load courses from the JSON file.
     * If file doesn't exist, create an empty one.
     * 
     * @return List of courses
     * @throws IOException if file operations fail
     */
    public List<Course> loadCourses() throws IOException {
        File file = new File(DATA_FILE);
        
        // Create empty file if it doesn't exist
        if (!file.exists()) {
            saveCourses(new ArrayList<>());
            return new ArrayList<>();
        }
        
        try {
            // Read and parse JSON file
            return objectMapper.readValue(file, new TypeReference<List<Course>>() {});
        } catch (IOException e) {
            // If file is corrupted, return empty list
            System.err.println("Error reading courses file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Save courses to the JSON file.
     * 
     * @param courses List of courses to save
     * @throws IOException if file operations fail
     */
    public void saveCourses(List<Course> courses) throws IOException {
        try {
            // Write JSON with pretty printing (indentation)
            objectMapper.writerWithDefaultPrettyPrinter()
                       .writeValue(new File(DATA_FILE), courses);
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
            throw e;
        }
    }
    
    // ============================================================================
    // HELPER METHODS
    // ============================================================================
    
    /**
     * Generate the next available ID for a new course.
     * 
     * @param courses Current list of courses
     * @return Next available ID
     */
    public Integer getNextId(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return 1;
        }
        
        // Find the maximum ID and add 1
        return courses.stream()
                     .mapToInt(Course::getId)
                     .max()
                     .orElse(0) + 1;
    }
    
    /**
     * Find a course by its ID.
     * 
     * @param courses List of courses to search
     * @param courseId ID to search for
     * @return Optional containing the course if found
     */
    public Optional<Course> findCourseById(List<Course> courses, Integer courseId) {
        if (courses == null || courseId == null) {
            return Optional.empty();
        }
        
        return courses.stream()
                     .filter(course -> course.getId().equals(courseId))
                     .findFirst();
    }
    
    /**
     * Find the index of a course by its ID.
     * 
     * @param courses List of courses to search
     * @param courseId ID to search for
     * @return Index of the course, or -1 if not found
     */
    public int findCourseIndex(List<Course> courses, Integer courseId) {
        if (courses == null || courseId == null) {
            return -1;
        }
        
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(courseId)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Validate that required fields are present and valid.
     * 
     * @param course Course to validate
     * @param checkRequired Whether to check for required fields
     * @return Error message if validation fails, null otherwise
     */
    public String validateCourse(Course course, boolean checkRequired) {
        if (course == null) {
            return "No course data provided";
        }
        
        // List of valid status values
        List<String> validStatuses = List.of("Not Started", "In Progress", "Completed");
        
        // Check required fields
        if (checkRequired) {
            if (course.getName() == null || course.getName().trim().isEmpty()) {
                return "Missing required field: name";
            }
            if (course.getDescription() == null || course.getDescription().trim().isEmpty()) {
                return "Missing required field: description";
            }
            if (course.getTargetDate() == null || course.getTargetDate().trim().isEmpty()) {
                return "Missing required field: target_date";
            }
            if (course.getStatus() == null || course.getStatus().trim().isEmpty()) {
                return "Missing required field: status";
            }
        }
        
        // Validate status if provided
        if (course.getStatus() != null && !validStatuses.contains(course.getStatus())) {
            return "Status must be one of: " + String.join(", ", validStatuses);
        }
        
        return null; // Validation passed
    }
    
    // ============================================================================
    // BONUS METHODS (Optional)
    // ============================================================================
    
    /**
     * Get statistics about courses.
     * 
     * @param courses List of courses
     * @return Map of statistics
     */
    public java.util.Map<String, Object> getStatistics(List<Course> courses) {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        
        stats.put("total_courses", courses.size());
        stats.put("not_started", courses.stream()
                .filter(c -> "Not Started".equals(c.getStatus()))
                .count());
        stats.put("in_progress", courses.stream()
                .filter(c -> "In Progress".equals(c.getStatus()))
                .count());
        stats.put("completed", courses.stream()
                .filter(c -> "Completed".equals(c.getStatus()))
                .count());
        
        return stats;
    }
    
    /**
     * Search courses by name or description.
     * 
     * @param courses List of courses
     * @param searchQuery Search term
     * @return List of matching courses
     */
    public List<Course> searchCourses(List<Course> courses, String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String query = searchQuery.toLowerCase();
        
        return courses.stream()
                     .filter(course -> 
                         course.getName().toLowerCase().contains(query) ||
                         course.getDescription().toLowerCase().contains(query))
                     .toList();
    }
}
