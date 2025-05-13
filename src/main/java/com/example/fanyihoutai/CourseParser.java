package com.example.fanyihoutai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CourseParser {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Course {
        private int serialNumber;
        private String semester;
        private String courseId;
        private String courseName;
        private String score;
        private String scoreIdentifier;
        private int credit;
        private int totalHours;
        private double gradePoint;
        private String retakeSemester;
        private String assessmentMethod;
        private String examNature;
        private String courseAttribute;
        private String courseNature;
        private String generalElectiveCategory;

    }}

