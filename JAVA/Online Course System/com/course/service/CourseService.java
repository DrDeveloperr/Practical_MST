package com.course.service;

import com.course.exception.*;
import com.course.model.Course;
import com.course.model.Student;
import java.io.*;
import java.util.ArrayList;

public class CourseService {

    ArrayList<Course> courses = new ArrayList<>();
    String fileName = "courses.txt";

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void enrollStudent(int courseId, Student s)
            throws CourseFullException, CourseNotFoundException, DuplicateEnrollmentException {

        Course foundCourse = null;

        for (Course c : courses) {
            if (c.getCourseId() == courseId) {
                foundCourse = c;
            }
        }

        if (foundCourse == null) {
            throw new CourseNotFoundException("Course not found");
        }

        if (foundCourse.getEnrolledStudents() >= foundCourse.getMaxSeats()) {
            throw new CourseFullException("Course is full");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                int cid = Integer.parseInt(data[0]);
                int sid = Integer.parseInt(data[2]);

                if (cid == courseId && sid == s.getStudentId()) {
                    br.close();
                    throw new DuplicateEnrollmentException("Student already enrolled");
                }
            }

            br.close();

        } catch (IOException e) {
        }

        foundCourse.setEnrolledStudents(foundCourse.getEnrolledStudents() + 1);

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));

            bw.write(courseId + "," +
                    foundCourse.getCourseName() + "," +
                    s.getStudentId() + "," +
                    s.getStudentName());

            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("File error");
        }
    }

    public void displayCourses() {
        for (Course c : courses) {
            c.displayCourse();
        }
    }

    public void viewCourses() {

        try {

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("No records found");
        }
    }
}