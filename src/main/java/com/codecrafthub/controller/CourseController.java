package com.codecrafthub.controller;

import com.codecrafthub.model.Course;
import com.codecrafthub.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * CourseController - REST API Controller
 * Handles HTTP requests for course management
 */
@RestController
@RequestMapping("")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    // ============================================================================
    // HOME ENDPOINT
    // ============================================================================
    
    /**
     * Home endpoint - provides API information
     * GET /
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Welcome to CodeCraftHub API!");
        response.put("version", "1.0");
        
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("GET /api/courses", "Get all courses");
        endpoints.put("GET /api/courses/<id>", "Get a specific course");
        endpoints.put("POST /api/courses", "Add a new course");
        endpoints.put("PUT /api/courses/<id>", "Update a course");
        endpoints.put("DELETE /api/courses/<id>", "Delete a course");
        
        response.put("endpoints", endpoints);
        
        return ResponseEntity.ok(response);
    }
    
    // ============================================================================
    // API ENDPOINTS
    // ============================================================================
    
    /**
     * GET /api/courses
     * Retrieve all courses
     */
    @GetMapping("/api/courses")
    public ResponseEntity<Map<String, Object>> getAllCourses() {
        try {
            List<Course> courses = courseService.loadCourses();
            
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("success", true);
            response.put("count", courses.size());
            response.put("courses", courses);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to retrieve courses: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    /**
     * GET /api/courses/{id}
     * Retrieve a specific course by ID
     */
    @GetMapping("/api/courses/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourse(@PathVariable Integer courseId) {
        try {
            List<Course> courses = courseService.loadCourses();
            Optional<Course> course = courseService.findCourseById(courses, courseId);
            
            if (course.isPresent()) {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("success", true);
                response.put("course", course.get());
                return ResponseEntity.ok(response);
            } else {
                return createErrorResponse(
                    "Course with ID " + courseId + " not found",
                    HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to retrieve course: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    /**
     * POST /api/courses
     * Add a new course
     * 
     * Required fields in JSON body:
     * - name: Course name
     * - description: Course description
     * - target_date: Target completion date (YYYY-MM-DD)
     * - status: Course status (Not Started, In Progress, Completed)
     */
    @PostMapping("/api/courses")
    public ResponseEntity<Map<String, Object>> addCourse(@RequestBody Course course) {
        try {
            // Validate course data
            String validationError = courseService.validateCourse(course, true);
            if (validationError != null) {
                return createErrorResponse(validationError, HttpStatus.BAD_REQUEST);
            }
            
            // Load existing courses
            List<Course> courses = courseService.loadCourses();
            
            // Set auto-generated fields
            course.setId(courseService.getNextId(courses));
            course.setCreatedAt(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            // Add to courses list
            courses.add(course);
            
            // Save to file
            courseService.saveCourses(courses);
            
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("success", true);
            response.put("message", "Course added successfully");
            response.put("course", course);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to add course: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    /**
     * PUT /api/courses/{id}
     * Update an existing course
     * 
     * You can update any of these fields:
     * - name
     * - description
     * - target_date
     * - status
     */
    @PutMapping("/api/courses/{courseId}")
    public ResponseEntity<Map<String, Object>> updateCourse(
            @PathVariable Integer courseId,
            @RequestBody Course updatedCourse) {
        try {
            // Load existing courses
            List<Course> courses = courseService.loadCourses();
            Optional<Course> existingCourse = courseService.findCourseById(courses, courseId);
            
            if (!existingCourse.isPresent()) {
                return createErrorResponse(
                    "Course with ID " + courseId + " not found",
                    HttpStatus.NOT_FOUND
                );
            }
            
            // Validate status if being updated
            if (updatedCourse.getStatus() != null) {
                String validationError = courseService.validateCourse(updatedCourse, false);
                if (validationError != null) {
                    return createErrorResponse(validationError, HttpStatus.BAD_REQUEST);
                }
            }
            
            Course course = existingCourse.get();
            
            // Update course fields if provided
            if (updatedCourse.getName() != null) {
                course.setName(updatedCourse.getName());
            }
            if (updatedCourse.getDescription() != null) {
                course.setDescription(updatedCourse.getDescription());
            }
            if (updatedCourse.getTargetDate() != null) {
                course.setTargetDate(updatedCourse.getTargetDate());
            }
            if (updatedCourse.getStatus() != null) {
                course.setStatus(updatedCourse.getStatus());
            }
            
            // Save to file
            courseService.saveCourses(courses);
            
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("success", true);
            response.put("message", "Course updated successfully");
            response.put("course", course);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to update course: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    /**
     * DELETE /api/courses/{id}
     * Delete a course
     */
    @DeleteMapping("/api/courses/{courseId}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Integer courseId) {
        try {
            // Load existing courses
            List<Course> courses = courseService.loadCourses();
            Optional<Course> courseToDelete = courseService.findCourseById(courses, courseId);
            
            if (!courseToDelete.isPresent()) {
                return createErrorResponse(
                    "Course with ID " + courseId + " not found",
                    HttpStatus.NOT_FOUND
                );
            }
            
            // Remove the course
            courses.remove(courseToDelete.get());
            
            // Save to file
            courseService.saveCourses(courses);
            
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("success", true);
            response.put("message", "Course deleted successfully");
            response.put("deleted_course", courseToDelete.get());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to delete course: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    // ============================================================================
    // BONUS ENDPOINTS (Optional enhancements)
    // ============================================================================
    
    /**
     * GET /api/courses/stats
     * Get statistics about your courses
     */
    @GetMapping("/api/courses/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            List<Course> courses = courseService.loadCourses();
            Map<String, Object> statistics = courseService.getStatistics(courses);
            
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("success", true);
            response.put("statistics", statistics);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to get statistics: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    /**
     * GET /api/courses/search?q=searchterm
     * Search courses by name or description
     */
    @GetMapping("/api/courses/search")
    public ResponseEntity<Map<String, Object>> searchCourses(@RequestParam String q) {
        try {
            if (q == null || q.trim().isEmpty()) {
                return createErrorResponse(
                    "Search query parameter \"q\" is required",
                    HttpStatus.BAD_REQUEST
                );
            }
            
            List<Course> courses = courseService.loadCourses();
            List<Course> results = courseService.searchCourses(courses, q);
            
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("success", true);
            response.put("count", results.size());
            response.put("courses", results);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse(
                "Failed to search courses: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    // ============================================================================
    // ERROR HANDLERS
    // ============================================================================
    
    /**
     * Handle 404 errors
     */
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound() {
        return createErrorResponse("Endpoint not found", HttpStatus.NOT_FOUND);
    }
    
    /**
     * Helper method to create error responses
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", false);
        response.put("error", message);
        return ResponseEntity.status(status).body(response);
    }
}
