package com.codecrafthub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Course Model Class
 * Represents a course in the learning platform
 */
public class Course {
    
    // Course ID (auto-generated)
    private Integer id;
    
    // Course name (required)
    private String name;
    
    // Course description (required)
    private String description;
    
    // Target completion date (required, format: YYYY-MM-DD)
    @JsonProperty("target_date")
    private String targetDate;
    
    // Course status (required: "Not Started", "In Progress", or "Completed")
    private String status;
    
    // Creation timestamp (auto-generated)
    @JsonProperty("created_at")
    private String createdAt;
    
    // ============================================================================
    // CONSTRUCTORS
    // ============================================================================
    
    /**
     * Default constructor
     */
    public Course() {
    }
    
    /**
     * Constructor with all fields
     */
    public Course(Integer id, String name, String description, String targetDate, String status, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.targetDate = targetDate;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    // ============================================================================
    // GETTERS AND SETTERS
    // ============================================================================
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTargetDate() {
        return targetDate;
    }
    
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
